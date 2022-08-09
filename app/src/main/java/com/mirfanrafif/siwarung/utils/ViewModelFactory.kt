package com.mirfanrafif.siwarung.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mirfanrafif.siwarung.core.domain.menu.MenuUseCase
import com.mirfanrafif.siwarung.core.domain.user.UserUseCase
import com.mirfanrafif.siwarung.di.AppScope
import com.mirfanrafif.siwarung.view.login.LoginViewModel
import com.mirfanrafif.siwarung.view.productlist.ProductListViewModel
import javax.inject.Inject

@AppScope
class ViewModelFactory @Inject constructor(private val menuUseCase: MenuUseCase, private val userUseCase: UserUseCase): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(ProductListViewModel::class.java) -> ProductListViewModel(menuUseCase) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(userUseCase) as T
            else -> throw Throwable("Unknown viewmodel class")
        }
    }
}