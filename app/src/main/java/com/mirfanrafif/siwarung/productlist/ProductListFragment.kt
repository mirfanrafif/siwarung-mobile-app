package com.mirfanrafif.siwarung.productlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mirfanrafif.siwarung.R
import com.mirfanrafif.siwarung.SiwarungApp
import com.mirfanrafif.siwarung.core.domain.menu.Category
import com.mirfanrafif.siwarung.core.domain.menu.Product
import com.mirfanrafif.siwarung.databinding.FragmentProductListBinding
import com.mirfanrafif.siwarung.utils.Status
import com.mirfanrafif.siwarung.utils.ViewModelFactory
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: ProductListViewModel by viewModels<ProductListViewModel>({
        activity as MainActivity
    }) {
        factory
    }

    private lateinit var adapter: ProductListAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        (requireActivity().application as SiwarungApp).appComponent.inject(this)
        binding = FragmentProductListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            adapter = ProductListAdapter(::addToCart)
            binding.rvProductList.layoutManager = GridLayoutManager(requireActivity(), 2)
            binding.rvProductList.adapter = adapter

            spinnerAdapter = ArrayAdapter(
                requireActivity(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                arrayListOf("Semua Produk")
            )
            binding.spCategory.adapter = spinnerAdapter
            binding.spCategory.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.setCategory(parent.getItemAtPosition(position).toString())
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }

            viewModel.getCategory().observe(viewLifecycleOwner) {
                adapter.setProductCategory(it)
            }

            viewModel.getKeyword().observe(viewLifecycleOwner) {
                adapter.setSearchKeyword(it)
            }

            viewModel.getCart().observe(viewLifecycleOwner) { cartList ->
                if (cartList.isNotEmpty()) {
                    binding.llTotal.visibility = View.VISIBLE
                    binding.tvTotal.text = NumberFormat.getCurrencyInstance(
                        Locale("id", "ID")
                    ).also { it.maximumFractionDigits = 0 }
                        .format(cartList.map { it.product.price * it.count }
                            .reduce { acc, i -> acc + i })
                } else {
                    binding.llTotal.visibility = View.GONE
                }
            }

            binding.edtSearchProduct.doOnTextChanged { text, start, before, count ->
                Log.d(ProductListFragment::class.simpleName, "Teks: $text")
                viewModel.setKeyword(text.toString())
            }

            binding.llTotal.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.wrapper, ProductCartFragment(), null).addToBackStack(null).commit()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllProducts().observe(viewLifecycleOwner) { productListResponse ->
            when (productListResponse.status) {
                Status.LOADING -> {
                    binding.loadingProduct.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.loadingProduct.visibility = View.GONE
                    if (productListResponse.data != null) {
                        adapter.setProductList(productListResponse.data)
                        setSpinnerCategory(productListResponse.data)
                    }
                }
                Status.ERROR -> {
                    binding.loadingProduct.visibility = View.GONE
                    Snackbar.make(
                        binding.root,
                        productListResponse.message ?: "",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun addToCart(product: Product) {
        viewModel.addProductToCart(product)
    }


    private fun setSpinnerCategory(productList: List<Product>) {
        val categoryList =
            productList.map { product -> product.category }
        val finalCategory = arrayListOf<Category>(Category(0, "Semua Produk"))
        categoryList.forEach { category ->
            if (finalCategory.find { it.id == category.id } == null) {
                finalCategory.add(category)
            }
        }
        spinnerAdapter.clear()
        spinnerAdapter.addAll(finalCategory.map { it.name })
    }

}