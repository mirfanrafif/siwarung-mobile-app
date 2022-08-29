package com.mirfanrafif.siwarung.domain.usecases.menu

import com.mirfanrafif.siwarung.core.data.remote.requests.TransactionDetailRequest
import com.mirfanrafif.siwarung.core.data.remote.requests.TransactionRequest
import com.mirfanrafif.siwarung.core.data.remote.requests.TransactionRequestV2
import com.mirfanrafif.siwarung.core.data.remote.responses.ProductResponse
import com.mirfanrafif.siwarung.domain.entities.Cart
import com.mirfanrafif.siwarung.domain.entities.Category
import com.mirfanrafif.siwarung.domain.entities.Product

object MenuMapper {
    fun mapResponseToDomain(response: ProductResponse?): Product {
        val category = Category(response?.category?.id ?: 0, response?.category?.name ?: "")
        return Product(
            id = response?.id ?: 0,
            name = response?.name ?: "",
            price = response?.price ?: 0,
            category = category
        )
    }

    fun mapCartToTransactionRequest(cartList: List<Cart>): TransactionRequest {
        val items = cartList.map { cart ->
            TransactionDetailRequest(
                productId = cart.product.id,
                count = cart.count
            )
        }

        return TransactionRequest(items)
    }

    fun mapCartToTransactionRequestV2(cartList: List<Cart>, cash: Int): TransactionRequestV2 {
        val items = cartList.map { cart ->
            TransactionDetailRequest(
                productId = cart.product.id,
                count = cart.count
            )
        }

        return TransactionRequestV2(items, cash)
    }
}