package com.mirfanrafif.siwarung.view.productlist

import com.mirfanrafif.siwarung.core.domain.entities.Product

data class ProductCart(
    val product: Product,
    val count: Int
)
