package com.mirfanrafif.siwarung.view.productlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mirfanrafif.siwarung.R
import com.mirfanrafif.siwarung.SiwarungApp
import com.mirfanrafif.siwarung.databinding.FragmentProductListBinding
import com.mirfanrafif.siwarung.core.domain.entities.Category
import com.mirfanrafif.siwarung.core.domain.entities.Product
import com.mirfanrafif.siwarung.core.repository.Status
import com.mirfanrafif.siwarung.utils.ViewModelFactory
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: ProductListViewModel by viewModels<ProductListViewModel>({
        activity as ProductListActivity
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
            adapter = ProductListAdapter()
            adapter.eventListener = object : ProductListAdapter.ProductItemEventListener {
                override fun onProductItemClick(product: Product) {
                    addToCart(product)
                }

            }
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

                    }
                }

            binding.edtSearchProduct.doOnTextChanged { text, _, _, _ ->
                Log.d(ProductListFragment::class.simpleName, "Teks: $text")
                viewModel.setKeyword(text.toString())
            }

            binding.llTotal.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.wrapper, ProductCartFragment(), null).addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.loadingProduct.visibility = View.VISIBLE
        viewModel.products.observe(viewLifecycleOwner) { productListResponse ->
            if(!productListResponse.loading) {
                binding.loadingProduct.visibility = View.GONE
            }
            if(productListResponse.data != null) {
                adapter.productListToShow = productListResponse.data
                val cartList = productListResponse.data.filter {
                    it.count > 0
                }
                setSpinnerCategory(productList = productListResponse.data)
                if ( cartList.isNotEmpty()) {
                    binding.llTotal.visibility = View.VISIBLE
                    binding.tvTotal.text = NumberFormat.getCurrencyInstance(
                        Locale("id", "ID")
                    ).also { it.maximumFractionDigits = 0 }
                        .format(cartList.map { it.product.price * it.count }
                            .reduce { acc, i -> acc + i })
                }
                else {
                    binding.llTotal.visibility = View.GONE
                }
            }
        }
    }

    private fun addToCart(product: Product) {
        viewModel.addProductToCart(product)
    }


    private fun setSpinnerCategory(productList: List<ProductCart>) {
        val categoryList =
            productList.map { product -> product.product.category }
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