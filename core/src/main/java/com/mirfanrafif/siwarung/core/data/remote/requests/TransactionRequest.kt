package com.mirfanrafif.siwarung.core.data.remote.requests

data class TransactionDetailRequest(
    val productId: Int,
    val count: Int
)

data class TransactionRequest(
    val items: List<TransactionDetailRequest>
)

data class TransactionRequestV2(
    val items: List<TransactionDetailRequest>,
    val cash: Int
)

