package com.mirfanrafif.siwarung.core.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ApiErrorResponse(

	@field:SerializedName("message")
	val message: String? = null
)
