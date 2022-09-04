package com.mirfanrafif.siwarung.core.data.local.entities

data class UserEntity(
    val id: Int,
    val name: String,
    val username: String,
    val role: String,
    val warung: WarungEntity
)

data class WarungEntity(
    val id: Int,
    val name: String,
    val address: String
)