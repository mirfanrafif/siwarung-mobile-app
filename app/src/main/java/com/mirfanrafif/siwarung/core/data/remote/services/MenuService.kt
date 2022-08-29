package com.mirfanrafif.siwarung.core.data.remote.services

import com.mirfanrafif.siwarung.core.data.remote.requests.TransactionRequest
import com.mirfanrafif.siwarung.core.data.remote.requests.TransactionRequestV2
import com.mirfanrafif.siwarung.core.data.remote.responses.AddTransactionResponse
import com.mirfanrafif.siwarung.core.data.remote.responses.ListProductResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MenuService {
    @GET("products")
    suspend fun getAllProducts(@Header("Authorization") token: String): ListProductResponse

    @POST("transactions")
    suspend fun addTransactions(
        @Header("Authorization") token: String,
        @Body request: TransactionRequest
    ): AddTransactionResponse

    @POST("transactions-v2")
    suspend fun addTransactions(
        @Header("Authorization") token: String,
        @Body request: TransactionRequestV2
    ): AddTransactionResponse
}