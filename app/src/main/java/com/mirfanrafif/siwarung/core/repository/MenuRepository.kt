package com.mirfanrafif.siwarung.core.repository

import android.util.Log
import com.mirfanrafif.siwarung.core.data.remote.MenuRemoteDataSource
import com.mirfanrafif.siwarung.core.data.remote.TransactionRequest
import com.mirfanrafif.siwarung.core.data.remote.responses.StatusResponse
import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.core.domain.menu.Category
import com.mirfanrafif.siwarung.core.domain.menu.MenuMapper
import com.mirfanrafif.siwarung.core.domain.menu.Product
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepository @Inject constructor(private val remote: MenuRemoteDataSource) :
    IMenuRepository {
    override fun getAllProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.loading(arrayListOf()))
        val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiaWF0IjoxNjU5ODg1NTk5LCJleHAiOjE2NTk5NzE5OTl9.y5XXEeQUJyNPmdWG8AVUohyo0C-ThwFEOExLxgvXBK0"
        val response = remote.getAllMenus(token).first()
        if (response.status == StatusResponse.SUCCESS) {
            val productList = response.body.map {
                MenuMapper.mapResponseToDomain(it)
            }
            emit(Resource.success(productList))
            return@flow
        }
        emit(
            Resource.error(
                "Gagal mengambil data dari API. ${response.message}",
                arrayListOf()
            )
        )
    }.flowOn(Dispatchers.Default)

    override fun addTransactions(transactionRequest: TransactionRequest): Flow<Resource<TransactionResponse>> = flow {
        emit(Resource.loading(TransactionResponse()))
        val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiaWF0IjoxNjU5ODg1NTk5LCJleHAiOjE2NTk5NzE5OTl9.y5XXEeQUJyNPmdWG8AVUohyo0C-ThwFEOExLxgvXBK0"
        val response = remote.addTransactions(token, transactionRequest).first()
        if (response.status == StatusResponse.SUCCESS) {
            emit(Resource.success(response.body))
            return@flow
        }
        emit(
            Resource.error(
                "Gagal mengambil data dari API. ${response.message}",
                TransactionResponse()
            )
        )
    }.flowOn(Dispatchers.Default)

}