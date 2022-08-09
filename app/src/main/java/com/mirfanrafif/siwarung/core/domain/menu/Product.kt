package com.mirfanrafif.siwarung.core.domain.menu

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val category: Category
)

data class Category(
    val id: Int,
    val name: String
)
