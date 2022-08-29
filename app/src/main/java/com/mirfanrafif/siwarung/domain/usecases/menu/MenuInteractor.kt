package com.mirfanrafif.siwarung.domain.usecases.menu

import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.core.repository.IMenuRepository
import com.mirfanrafif.siwarung.domain.entities.Cart
import com.mirfanrafif.siwarung.domain.entities.Product
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MenuInteractor @Inject constructor(private val repository: IMenuRepository): MenuUseCase {
    override fun getAllProducts(): Flow<Resource<List<Product>>> {
        return repository.getAllProducts()
    }

    override fun addTransactions(cartList: List<Cart>): Flow<Resource<TransactionResponse>> {
        val request = MenuMapper.mapCartToTransactionRequest(cartList)
        return repository.addTransactions(request)
    }

    override fun addTransactions(
        cartList: List<Cart>,
        cash: Int
    ): Flow<Resource<TransactionResponse>> {
        val request = MenuMapper.mapCartToTransactionRequestV2(cartList, cash)
        return repository.addTransactions(request)
    }
}