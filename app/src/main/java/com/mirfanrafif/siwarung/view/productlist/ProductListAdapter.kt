package com.mirfanrafif.siwarung.view.productlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirfanrafif.siwarung.core.domain.entities.Product
import com.mirfanrafif.siwarung.databinding.ItemProductBinding
import com.mirfanrafif.siwarung.core.domain.entities.Cart
import com.mirfanrafif.siwarung.utils.BindableHolder
import com.mirfanrafif.siwarung.utils.CurrencyHelper
import kotlin.collections.ArrayList

class ProductListAdapter() :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    var productListToShow: ArrayList<ProductCart> = arrayListOf()
        set(value) {
            productListToShow.clear()
            productListToShow.addAll(value)
            notifyDataSetChanged()
        }

    var eventListener: ProductItemEventListener? = null

    interface ProductItemEventListener {
        fun onProductItemClick(product: Product)
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root), BindableHolder {

        init {
            binding.cardItemProduct.setOnClickListener {
                eventListener?.onProductItemClick(product = productListToShow[this.layoutPosition].product)
            }
        }

        override fun bind(position: Int) {
            val productCart = productListToShow[position]
            binding.tvItemName.text = productCart.product.name
            binding.tvItemPrice.text = CurrencyHelper.formatPrice(productCart.product.price)
            if(productCart.count > 0) {
                binding.tvItemProductCartCount.visibility = View.VISIBLE
                binding.tvItemProductCartCount.text = productCart.count.toString()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        (holder as BindableHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return productListToShow.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}