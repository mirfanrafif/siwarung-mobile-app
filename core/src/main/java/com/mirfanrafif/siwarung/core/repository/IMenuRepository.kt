package com.mirfanrafif.siwarung.core.repository

import com.mirfanrafif.siwarung.core.data.remote.requests.TransactionRequest
import com.mirfanrafif.siwarung.core.data.remote.requests.TransactionRequestV2
import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.core.domain.entities.Product
import kotlinx.coroutines.flow.Flow

interface IMenuRepository {
    fun getAllProducts(): Flow<Resource<List<Product>>>

    fun addTransactions(transactionRequest: TransactionRequest): Flow<Resource<TransactionResponse>>

    fun addTransactions(transactionRequest: TransactionRequestV2): Flow<Resource<TransactionResponse>>
}