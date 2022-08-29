package com.mirfanrafif.siwarung.core.data.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddTransactionResponse(

	@field:SerializedName("data")
	val data: TransactionResponse? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class TransactionResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("details")
	val details: List<TransactionDetails?>? = null,

	@field:SerializedName("warung")
	val warung: Warung? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null,

	@field:SerializedName("receipt")
	val receipt: String? = null
) : Parcelable

@Parcelize
data class TransactionDetails(

	@field:SerializedName("product")
	val product: ProductResponse? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null
) : Parcelable

@Parcelize
data class User(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("warung")
	val warung: Warung? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
