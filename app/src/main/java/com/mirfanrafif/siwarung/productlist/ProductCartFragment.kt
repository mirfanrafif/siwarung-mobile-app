package com.mirfanrafif.siwarung.productlist

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.google.android.material.snackbar.Snackbar
import com.mirfanrafif.siwarung.R
import com.mirfanrafif.siwarung.SiwarungApp
import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.databinding.FragmentProductCartBinding
import com.mirfanrafif.siwarung.databinding.FragmentProductListBinding
import com.mirfanrafif.siwarung.utils.CurrencyHelper
import com.mirfanrafif.siwarung.utils.Status
import com.mirfanrafif.siwarung.utils.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class ProductCartFragment : Fragment() {

    private lateinit var binding: FragmentProductCartBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: ProductListViewModel by viewModels({
        activity as MainActivity
    }) {
        factory
    }

    private lateinit var adapter: ProductCartAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as SiwarungApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProductCartAdapter()
        binding.rvCart.adapter = adapter
        binding.rvCart.layoutManager = LinearLayoutManager(context)
        if (activity != null) {
            viewModel.getCart().observe(viewLifecycleOwner) { cartlist ->
                adapter.setProductList(cartlist)
                val total =
                    if (cartlist.isNotEmpty()) cartlist.map { cart -> cart.count * cart.product.price }
                        .reduce { acc, i -> acc + i } else 0
                val formattedTotal = CurrencyHelper.formatPrice(total)
                binding.tvCartTotal.text = "Total: $formattedTotal"
                binding.btnBayar.setOnClickListener {
                    if (cartlist.isNotEmpty()) {
                        viewModel.selesaiTransaksi().observe(viewLifecycleOwner) { resource ->
                            when (resource.status) {
                                Status.LOADING -> {
                                    binding.btnBayar.isEnabled = false
                                }
                                Status.SUCCESS -> {
                                    val successDialog =
                                        AlertDialog.Builder(requireActivity()).also {
                                            it.setTitle("Berhasil melakukan transaksi")
                                            it.setMessage("Silahkan cetak struk dengan menekan tombol dibawah")
                                            it.setPositiveButton("Tutup") { dialog, _ ->
                                                dialog.cancel()
                                            }
                                            it.setNeutralButton("Cetak struk") { dialog, _ ->
                                                printStruk(resource.data!!, dialog)
                                                viewModel.clearCart()
                                            }
                                        }
                                    binding.btnBayar.isEnabled = true
                                    successDialog.show()


                                }
                                Status.ERROR -> {
                                    Snackbar.make(
                                        binding.root,
                                        "Gagal menyimpan transaksi",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun printStruk(transactionResponse: TransactionResponse, dialog: DialogInterface) {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.BLUETOOTH),
                101
            );
        } else if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.BLUETOOTH_ADMIN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.BLUETOOTH_ADMIN),
                102
            );
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                103
            );
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.BLUETOOTH_SCAN),
                104
            );
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                val printer =
                    EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32)

                val item = transactionResponse.details?.map {
                    val total = (it?.product?.price ?: 0) * (it?.count ?: 0)
                    """[L]${it?.product?.name?.take(15)}[R]${it?.count}[R]${
                        CurrencyHelper.formatPrice(
                            total
                        )
                    }
[L]@${CurrencyHelper.formatPrice(it?.product?.price ?: 0)}
[L]
"""
                }?.reduce { acc, s -> acc + s }
                val transactionTotal = transactionResponse.details?.map {
                    (it?.product?.price ?: 0) * (it?.count ?: 0)
                }?.reduce { acc, i -> acc + i }
                val textToPrint = """[L]
[C]<b>${transactionResponse.warung?.name}</b>
[C]${transactionResponse.warung?.address?.take(32)}
[L]
[C]================================
[L]
$item
[C]--------------------------------
[R]Total : ${CurrencyHelper.formatPrice(transactionTotal ?: 0)}
[L]
[C]================================
[L]
[C]<b>Terimakasih atas</b>
[C]<b>Kunjungannya</b>
[L]""".trimIndent()
                Log.d("Text to print", textToPrint)
//                printer.printFormattedText(textToPrint)
            }
        }
    }
}