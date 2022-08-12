package com.mirfanrafif.siwarung.view.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.domain.entities.Cart
import com.mirfanrafif.siwarung.domain.entities.Product
import com.mirfanrafif.siwarung.domain.usecases.menu.MenuUseCase
import com.mirfanrafif.siwarung.domain.usecases.user.getsession.GetSessionUseCase
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductListViewModel @Inject constructor(private val menuUseCase: MenuUseCase, private val getSessionUseCase: GetSessionUseCase): ViewModel() {
    fun getAllProducts(): LiveData<Resource<List<Product>>> = menuUseCase.getAllProducts().asLiveData()

    private val _category: MutableLiveData<String> = MutableLiveData()
    fun getCategory(): LiveData<String> = _category
    fun setCategory(newCategory: String) {
        _category.postValue(newCategory)
    }

    private val _searchKeyword: MutableLiveData<String> = MutableLiveData()
    fun getKeyword(): LiveData<String> = _searchKeyword
    fun setKeyword(newKeyword: String) {
        _searchKeyword.postValue(newKeyword)
    }

    private val _cart = MutableLiveData<ArrayList<Cart>?>(arrayListOf())
    fun getCart(): LiveData<ArrayList<Cart>?> = _cart
    fun addProductToCart(product: Product) {
        val cart = _cart.value
        if(cart != null) {
            val findCart = cart.find { item -> item.product.id == product.id }
            if(findCart != null) {
                val index = cart.indexOf(findCart)
                cart[index].count++
            }else{
                cart.add(Cart(product, 1))
            }

            _cart.postValue(cart)
        }
    }

    fun addCount(product: Product) {
        val cart = _cart.value
        if(cart != null) {
            val findCart = cart.find { item -> item.product.id == product.id }
            if(findCart != null) {
                val index = cart.indexOf(findCart)
                cart[index].count++
            }

            _cart.postValue(cart)
        }
    }

    fun subsCount(product: Product) {
        val cart = _cart.value
        if(cart != null) {
            val findCart = cart.find { item -> item.product.id == product.id }
            if(findCart != null) {
                val index = cart.indexOf(findCart)
                cart[index].count--
                if(cart[index].count == 0) cart.removeAt(index)
            }

            _cart.postValue(cart)
        }
    }

    fun selesaiTransaksi(): LiveData<Resource<TransactionResponse>> {
        val cart = _cart.value
        return menuUseCase.addTransactions(cart!!).asLiveData()
    }

    fun clearCart() {
        _cart.postValue(arrayListOf())
    }

    fun getWarung() = getSessionUseCase.getWarung()

}