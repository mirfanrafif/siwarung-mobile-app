package com.mirfanrafif.siwarung.productlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirfanrafif.siwarung.core.domain.menu.Cart
import com.mirfanrafif.siwarung.databinding.ItemCartBinding
import com.mirfanrafif.siwarung.utils.CurrencyHelper
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class ProductCartAdapter() :
    RecyclerView.Adapter<ProductCartAdapter.ProductCartViewHolder>() {

    private val cartList: ArrayList<Cart> = arrayListOf()

    fun setProductList(newProductList: List<Cart>) {
        cartList.clear()
        cartList.addAll(newProductList)
        notifyDataSetChanged()
    }


    inner class ProductCartViewHolder(private val binding: ItemCartBinding, context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart) {
            binding.tvCartItemName.text = cart.product.name
            binding.tvCartItemPrice.text = CurrencyHelper.formatPrice(cart.product.price)
            binding.tvCartCount.text = "Jumlah: ${cart.count}"
            binding.tvCartItemTotal.text = CurrencyHelper.formatPrice(cart.product.price * cart.count)
            //TODO("Onclick tambah produk")

            //TODO("Onclick kurangi produk")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductCartViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ProductCartViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}