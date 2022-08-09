package com.mirfanrafif.siwarung.core.data.remote

import android.util.Log
import com.mirfanrafif.siwarung.core.data.remote.responses.AddTransactionResponse
import com.mirfanrafif.siwarung.core.data.remote.responses.ApiResponse
import com.mirfanrafif.siwarung.core.data.remote.responses.ProductResponse
import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRemoteDataSource @Inject constructor(private val menuService: MenuService) {
    fun getAllMenus(token: String): Flow<ApiResponse<List<ProductResponse>>> {
        return flow {
            Log.d(MenuRemoteDataSource::class.simpleName, "Lewat sini")
            try {
                val response = menuService.getAllProducts(token)
                val productList = response.data
                if(productList != null && productList.isNotEmpty()) {
                    emit(ApiResponse.success(productList))
                }else{
                    emit(ApiResponse.empty("Tidak ada produk/menu", arrayListOf()))
                }
            }catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.error("Gagal mengambil produk. " + (e.message ?: ""), arrayListOf()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun addTransactions(token: String, transactionRequest: TransactionRequest): Flow<ApiResponse<TransactionResponse>> {
        return flow {
            Log.d(MenuRemoteDataSource::class.simpleName, "Lewat sini")
            try {
                val response = menuService.addTransactions(token, transactionRequest)
                val transactionResponse = response.data
                if(transactionResponse != null) {
                    emit(ApiResponse.success(transactionResponse))
                }else{
                    emit(ApiResponse.empty("Tidak ada produk/menu", TransactionResponse()))
                }
            }catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.error("Gagal mengambil produk. " + (e.message ?: ""), TransactionResponse()))
            }
        }.flowOn(Dispatchers.IO)
    }
}