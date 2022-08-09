package com.mirfanrafif.siwarung.core.data.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListProductResponse(

	@field:SerializedName("data")
	val data: List<ProductResponse>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class ProductResponse(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("category")
	val category: Category? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null
) : Parcelable

@Parcelize
data class Warung(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null
) : Parcelable

@Parcelize
data class Category(

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
	val deletedAt: String? = null
) : Parcelable
