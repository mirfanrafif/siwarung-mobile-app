package com.mirfanrafif.siwarung.core.data.remote

data class TransactionDetailRequest(
    val productId: Int,
    val count: Int
)

data class TransactionRequest(
    val items: List<TransactionDetailRequest>
)

