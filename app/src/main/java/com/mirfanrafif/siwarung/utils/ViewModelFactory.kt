package com.mirfanrafif.siwarung.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mirfanrafif.siwarung.domain.usecases.menu.MenuUseCase
import com.mirfanrafif.siwarung.domain.usecases.user.getsession.GetSessionUseCase
import com.mirfanrafif.siwarung.domain.di.AppScope
import com.mirfanrafif.siwarung.domain.usecases.user.login.LoginUseCase
import com.mirfanrafif.siwarung.view.login.LoginViewModel
import com.mirfanrafif.siwarung.view.productlist.ProductListViewModel
import javax.inject.Inject

@AppScope
class ViewModelFactory @Inject constructor(
    private val menuUseCase: MenuUseCase,
    private val getSessionUseCase: GetSessionUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductListViewModel::class.java) -> ProductListViewModel(
                menuUseCase, getSessionUseCase
            ) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(
                loginUseCase,
                getSessionUseCase
            ) as T
            else -> throw Throwable("Unknown viewmodel class")
        }
    }
}