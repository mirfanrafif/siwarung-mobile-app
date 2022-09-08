package com.mirfanrafif.siwarung.utils

data class UiState<T>(
    val loading: Boolean,
    val data: T? = null,
    val message: String? = null
)