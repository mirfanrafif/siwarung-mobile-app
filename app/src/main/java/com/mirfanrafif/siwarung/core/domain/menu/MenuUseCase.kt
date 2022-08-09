package com.mirfanrafif.siwarung.core.domain.menu

import com.mirfanrafif.siwarung.core.data.remote.responses.AddTransactionResponse
import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MenuUseCase {
    fun getAllProducts(): Flow<Resource<List<Product>>>

    fun addTransactions(cartList: List<Cart>): Flow<Resource<TransactionResponse>>
}