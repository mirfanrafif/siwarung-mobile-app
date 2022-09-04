package com.mirfanrafif.siwarung.core.data.remote.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginResponseData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class LoginResponseData(

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("token")
	val token: String? = null
)