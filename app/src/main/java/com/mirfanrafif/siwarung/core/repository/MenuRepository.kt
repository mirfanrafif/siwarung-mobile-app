package com.mirfanrafif.siwarung.core.repository

import com.mirfanrafif.siwarung.core.data.local.UserLocalDataSource
import com.mirfanrafif.siwarung.core.data.remote.MenuRemoteDataSource
import com.mirfanrafif.siwarung.core.data.remote.requests.TransactionRequest
import com.mirfanrafif.siwarung.core.data.remote.responses.StatusResponse
import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.core.domain.menu.MenuMapper
import com.mirfanrafif.siwarung.core.domain.menu.Product
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepository @Inject constructor(private val remote: MenuRemoteDataSource, private val userLocalDataSource: UserLocalDataSource) :
    IMenuRepository {
    override fun getAllProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.loading(arrayListOf()))
        val token = "Bearer " + userLocalDataSource.getToken()
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
        val token = "Bearer " + userLocalDataSource.getToken()
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