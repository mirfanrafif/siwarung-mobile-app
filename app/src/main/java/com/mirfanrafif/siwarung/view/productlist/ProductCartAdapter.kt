package com.mirfanrafif.siwarung.view.productlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirfanrafif.siwarung.core.domain.entities.Cart
import com.mirfanrafif.siwarung.databinding.ItemCartBinding
import com.mirfanrafif.siwarung.utils.CurrencyHelper
import kotlin.collections.ArrayList


class ProductCartAdapter(private val viewModel: ProductListViewModel) :
    RecyclerView.Adapter<ProductCartAdapter.ProductCartViewHolder>() {

    private val cartList: ArrayList<ProductCart> = arrayListOf()

    fun setProductList(newProductList: List<ProductCart>) {
        cartList.clear()
        cartList.addAll(newProductList)
        notifyDataSetChanged()
    }


    inner class ProductCartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: ProductCart) {
            binding.tvCartItemName.text = cart.product.name
            binding.tvCartItemPrice.text = CurrencyHelper.formatPrice(cart.product.price)
            binding.tvCartCount.text = "Jumlah: ${cart.count}"
            binding.tvCartItemTotal.text = CurrencyHelper.formatPrice(cart.product.price * cart.count)
            binding.btnCartAdd.setOnClickListener {
                viewModel.addCount(cart.product)
            }
            binding.btnCartSubs.setOnClickListener {
                viewModel.subsCount(cart.product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductCartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductCartViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}