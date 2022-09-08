package com.mirfanrafif.siwarung.view.productlist

import androidx.lifecycle.*
import com.mirfanrafif.siwarung.core.data.remote.responses.TransactionResponse
import com.mirfanrafif.siwarung.core.domain.entities.Cart
import com.mirfanrafif.siwarung.core.domain.entities.Product
import com.mirfanrafif.siwarung.core.domain.usecases.menu.MenuUseCase
import com.mirfanrafif.siwarung.core.domain.usecases.user.getsession.GetSessionUseCase
import com.mirfanrafif.siwarung.core.repository.Resource
import com.mirfanrafif.siwarung.core.repository.Status
import com.mirfanrafif.siwarung.utils.UiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductListViewModel @Inject constructor(private val menuUseCase: MenuUseCase, private val getSessionUseCase: GetSessionUseCase): ViewModel() {
     val products: MutableLiveData<UiState<ArrayList<ProductCart>>> = MutableLiveData(UiState(loading = true))

    init {
        viewModelScope.launch {
            menuUseCase.getAllProducts().collect { productList ->
                when(productList.status) {
                    Status.SUCCESS -> {
                        val data = arrayListOf<ProductCart>()
                        val cart = productList.data?.map {
                            ProductCart(it, 0)
                        }
                        data.addAll(cart?: listOf())
                        products.value = UiState(loading = false, data = data)
                    }
                    Status.ERROR -> {
                        products.value = UiState(loading = false, message = productList.message)
                    }
                    else -> {

                    }
                }
            }
        }
    }

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
        val cart = products.value?.data
        if(cart != null) {
            val findCart = cart.find { item -> item.product.id == product.id }
            if(findCart != null) {
                val index = cart.indexOf(findCart)
                cart[index] = ProductCart(cart[index].product, cart[index].count + 1)
            }
            products.postValue(UiState(loading = false, data =cart))
        }
    }

    fun addCount(product: Product) {
        val cart = products.value?.data
        if(cart != null) {
            val findCart = cart.find { item -> item.product.id == product.id }
            if(findCart != null) {
                val index = cart.indexOf(findCart)
                cart[index] = ProductCart(cart[index].product, cart[index].count + 1)
            }
            products.postValue(UiState(loading = false, data =cart))
        }
    }

    fun subsCount(product: Product) {
        val cart = products.value?.data
        if(cart != null) {
            val findCart = cart.find { item -> item.product.id == product.id }
            if(findCart != null) {
                val index = cart.indexOf(findCart)
                cart[index] = ProductCart(cart[index].product, cart[index].count - 1)
            }
            products.postValue(UiState(loading = false, data =cart))
        }
    }

    fun selesaiTransaksi(jumlahBayar: Int): LiveData<Resource<TransactionResponse>> {
        val cart =
            products.value?.data?.map {
            Cart(it.product, it.count)
        }?.filter { it.count > 0 }

        return menuUseCase.addTransactions(cart ?: arrayListOf(), jumlahBayar).asLiveData()
    }

    fun clearCart() {
        _cart.postValue(arrayListOf())
    }

    fun getWarung() = getSessionUseCase.getWarung()

}