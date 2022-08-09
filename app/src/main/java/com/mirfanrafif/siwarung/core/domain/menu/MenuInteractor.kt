package com.mirfanrafif.siwarung.core.domain.menu

import com.mirfanrafif.siwarung.core.data.remote.responses.ApiResponse
import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.core.repository.IMenuRepository
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class MenuInteractor @Inject constructor(private val repository: IMenuRepository): MenuUseCase {
    override fun getAllProducts(): Flow<Resource<List<Product>>> {
        return repository.getAllProducts()
    }

    override fun addTransactions(cartList: List<Cart>): Flow<Resource<TransactionResponse>> {
        val request = MenuMapper.mapCartToTransactionRequest(cartList)
        return repository.addTransactions(request)
    }
}