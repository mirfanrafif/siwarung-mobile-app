package com.mirfanrafif.siwarung.view.productlist

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.google.android.material.snackbar.Snackbar
import com.mirfanrafif.siwarung.SiwarungApp
import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.databinding.DialogJumlahBayarBinding
import com.mirfanrafif.siwarung.databinding.DialogTransactionSuccessBinding
import com.mirfanrafif.siwarung.databinding.FragmentProductCartBinding
import com.mirfanrafif.siwarung.core.domain.entities.Cart
import com.mirfanrafif.siwarung.core.repository.Status
import com.mirfanrafif.siwarung.utils.CurrencyHelper
import com.mirfanrafif.siwarung.utils.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductCartFragment : Fragment() {

    private lateinit var binding: FragmentProductCartBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: ProductListViewModel by viewModels({
        activity as ProductListActivity
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
        adapter = ProductCartAdapter(viewModel)
        binding.rvCart.adapter = adapter
        binding.rvCart.layoutManager = LinearLayoutManager(context)
        if (activity != null) {
            viewModel.getCart().observe(viewLifecycleOwner) { cartlist ->
                if(cartlist != null && cartlist.isNotEmpty()) {
                    binding.llEmptyCart.visibility = View.GONE
                    binding.rvCart.visibility = View.VISIBLE
                    adapter.setProductList(cartlist)
                    val total =
                        if (cartlist.isNotEmpty()) cartlist.map { cart -> cart.count * cart.product.price }
                            .reduce { acc, i -> acc + i } else 0
                    val formattedTotal = CurrencyHelper.formatPrice(total)
                    binding.tvCartTotal.text = "Total: $formattedTotal"
                    binding.btnBayar.setOnClickListener {
                        showDialogBayar(formattedTotal, total, cartlist)
                    }
                }else{
                    binding.llEmptyCart.visibility = View.VISIBLE
                    val formattedTotal = CurrencyHelper.formatPrice(0)
                    binding.tvCartTotal.text = "Total: $formattedTotal"
                    binding.rvCart.visibility = View.GONE
                }
            }
        }
    }

    private fun showDialogBayar(formattedTotal: String, total: Int, cartlist: List<Cart>) {
        val alertBinding = DialogJumlahBayarBinding.inflate(layoutInflater)
        alertBinding.tvDialogTotal.text = formattedTotal

        alertBinding.edtJumlahBayar.doOnTextChanged { text, _, _, _ ->
            if(text != null && text.isNotBlank()) {
                val jumlahBayar = alertBinding.edtJumlahBayar.text.toString().toInt()
                val kembalian = jumlahBayar - total
                alertBinding.tvKembalian.text = CurrencyHelper.formatPrice(kembalian)
            }
        }

        val hitungKembalianDialog =
            AlertDialog.Builder(requireActivity()).also {
                it.setView(alertBinding.root)
                it.setCancelable(false)
            }.create()
        alertBinding.btnDialogBayar.setOnClickListener {
            val jumlahBayar = alertBinding.edtJumlahBayar.text.toString().toInt()
            if(jumlahBayar < total) {
                alertBinding.edtJumlahBayar.error = "Jumlah bayar kurang"
            }else{
                alertBinding.edtJumlahBayar.error = null
                hitungKembalianDialog.cancel()
                if(cartlist.isNotEmpty()) {
                    simpanTransaksi(jumlahBayar)
                }
            }

        }
        alertBinding.btnDialogBatal.setOnClickListener {
            hitungKembalianDialog.cancel()
        }
        hitungKembalianDialog.show()
    }

    private fun simpanTransaksi(jumlahBayar: Int) {
        viewModel.selesaiTransaksi(jumlahBayar).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    binding.btnBayar.isEnabled = false
                }
                Status.SUCCESS -> {
                    val alertBinding = DialogTransactionSuccessBinding.inflate(layoutInflater)

                    val successDialog =
                        AlertDialog.Builder(requireActivity()).also {
                            it.setView(alertBinding.root)
                            it.setCancelable(false)
                        }.create()
                    alertBinding.btnCetakStruk.setOnClickListener {
                        printStruk(resource.data!!)
                    }
                    alertBinding.btnTutup.setOnClickListener {
                        successDialog.cancel()
                        viewModel.clearCart()
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

    private fun printStruk(transactionResponse: TransactionResponse) {
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

                val textToPrint = transactionResponse.receipt ?: ""
                Log.d("Text to print", textToPrint)
                printer.printFormattedText(textToPrint)
            }
        }
    }
//
//    private fun nameSplit(input: String, alignment: String, tag: String?): String {
//        val inputSplit = input.split(" ")
//        var output = ""
//        var wordCount = 0
//        inputSplit.forEach { s ->
//            if (tag != null && wordCount == 0) {
//                output += "<$tag>"
//            }
//            if((s.length + wordCount + 1) < 32) {
//                output += "$s "
//                wordCount += s.length + 1
//            }else{
//                if(tag != null) {
//                    output += "</$tag>"
//                }
//                output += "\n[$alignment]"
//                if(tag != null) {
//                    output += "<$tag>"
//                }
//                output += "$s "
//                wordCount = 0
//            }
//        }
//        if(tag != null) {
//            output += "</$tag>"
//        }
//        return output
//    }
//
//    private fun makeTextToPrint(transactionResponse: TransactionResponse): String {
//        val item = transactionResponse.details?.map { details ->
//            var nameSplit = (details?.product?.name ?: "").split(" ")
//            val total = (details?.product?.price ?: 0) * (details?.count ?: 0)
//            var finalString = "[L]"
//            var wordCount = 0
//            var line = 1
//            nameSplit.forEach { s ->
//                if ((s.length + wordCount + 1) < 15) {
//                    finalString += "$s "
//                    wordCount += s.length
//                } else {
//                    if (line == 1) {
//                        finalString += "[R]${details?.count}[R]${
//                            CurrencyHelper.formatPrice(
//                                total
//                            )
//                        }"
//                    }
//                    finalString += "\n[L]$s "
//                    line++
//                    wordCount = 0
//                }
//            }
//            if (line == 1) {
//                finalString += "[R]${details?.count}[R]${
//                    CurrencyHelper.formatPrice(
//                        total
//                    )
//                }"
//            }
//
//            finalString += "\n[L]@${CurrencyHelper.formatPrice(details?.product?.price ?: 0)}\n[L]"
//            finalString
//        }?.reduce { acc, s -> acc + "\n" + s }
//        val transactionTotal = transactionResponse.details?.map {
//            (it?.product?.price ?: 0) * (it?.count ?: 0)
//        }?.reduce { acc, i -> acc + i }
//
//        return """[L]
//[C]${nameSplit(transactionResponse.warung?.name ?: "", "C", "b")}
//[C]${nameSplit(transactionResponse.warung?.address ?: "", "C", null)}
//[L]
//[C]================================
//[L]
//$item
//[C]--------------------------------
//[R]Total : ${CurrencyHelper.formatPrice(transactionTotal ?: 0)}
//[L]
//[C]================================
//[L]
//[C]<b>Terimakasih atas</b>
//[C]<b>Kunjungannya</b>
//[L]""".trimIndent()
//    }
}