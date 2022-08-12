package com.mirfanrafif.siwarung.core.repository

import com.mirfanrafif.siwarung.core.data.remote.requests.TransactionRequest
import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.domain.entities.Product
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IMenuRepository {
    fun getAllProducts(): Flow<Resource<List<Product>>>

    fun addTransactions(transactionRequest: TransactionRequest): Flow<Resource<TransactionResponse>>
}