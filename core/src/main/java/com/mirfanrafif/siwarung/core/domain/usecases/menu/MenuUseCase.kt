package com.mirfanrafif.siwarung.core.domain.usecases.menu

import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.core.domain.entities.Cart
import com.mirfanrafif.siwarung.core.domain.entities.Product
import com.mirfanrafif.siwarung.core.repository.Resource
import kotlinx.coroutines.flow.Flow

interface MenuUseCase {
    fun getAllProducts(): Flow<Resource<List<Product>>>

    fun addTransactions(cartList: List<Cart>): Flow<Resource<TransactionResponse>>

    fun addTransactions(cartList: List<Cart>, cash: Int): Flow<Resource<TransactionResponse>>
}