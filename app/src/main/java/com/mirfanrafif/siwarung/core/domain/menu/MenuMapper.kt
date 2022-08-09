package com.mirfanrafif.siwarung.core.domain.menu

import com.mirfanrafif.siwarung.core.data.remote.TransactionDetailRequest
import com.mirfanrafif.siwarung.core.data.remote.TransactionRequest
import com.mirfanrafif.siwarung.core.data.remote.responses.ProductResponse

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
}