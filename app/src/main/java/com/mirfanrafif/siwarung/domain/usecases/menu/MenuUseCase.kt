package com.mirfanrafif.siwarung.domain.usecases.menu

import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.domain.entities.Cart
import com.mirfanrafif.siwarung.domain.entities.Product
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MenuUseCase {
    fun getAllProducts(): Flow<Resource<List<Product>>>

    fun addTransactions(cartList: List<Cart>): Flow<Resource<TransactionResponse>>
}