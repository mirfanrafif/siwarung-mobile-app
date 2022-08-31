package com.mirfanrafif.siwarung.view.productlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirfanrafif.siwarung.domain.entities.Product
import com.mirfanrafif.siwarung.databinding.ItemProductBinding
import com.mirfanrafif.siwarung.domain.entities.Cart
import com.mirfanrafif.siwarung.utils.CurrencyHelper
import kotlin.collections.ArrayList

class ProductListAdapter(val addProductToCart: (Product) -> Unit) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private val productList: ArrayList<Product> = arrayListOf()
    private val productListToShow: ArrayList<Product> = arrayListOf()
    private var category: String = "Semua Produk"
    private var keyword: String = ""

    private val cartList: ArrayList<Cart> = arrayListOf()

    fun setCart(newCart: List<Cart>) {
        cartList.clear()
        cartList.addAll(newCart)
        notifyDataSetChanged()
    }

    fun setProductList(newProductList: List<Product>) {
        productList.clear()
        productList.addAll(newProductList)
        showFilteredData()
    }

    fun setProductCategory(newCategory: String) {
        category = newCategory
        showFilteredData()
    }

    fun showFilteredData() {
        productListToShow.clear()
        productListToShow.addAll(productList.filter { if (category != "Semua Produk") it.category.name == category else true }
            .filter {
                if (keyword.isNotBlank()) it.name.contains(
                    keyword,
                    ignoreCase = true
                ) else true
            })
        notifyDataSetChanged()
    }

    fun setSearchKeyword(newKeyword: String) {
        keyword = newKeyword
        showFilteredData()
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding, context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvItemName.text = product.name
            binding.tvItemPrice.text = CurrencyHelper.formatPrice(product.price)
            val productInCart = cartList.find { cart -> cart.product.id == product.id }
            if(productInCart != null) {
                binding.tvItemProductCartCount.visibility = View.VISIBLE
                binding.tvItemProductCartCount.text = productInCart.count.toString()
            }
            binding.cardItemProduct.setOnClickListener {
                addProductToCart(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productListToShow[position])
    }

    override fun getItemCount(): Int {
        return productListToShow.size
    }
}