package com.mirfanrafif.siwarung.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mirfanrafif.siwarung.core.domain.menu.MenuUseCase
import com.mirfanrafif.siwarung.di.AppScope
import com.mirfanrafif.siwarung.productlist.ProductListViewModel
import javax.inject.Inject

@AppScope
class ViewModelFactory @Inject constructor(private val menuUseCase: MenuUseCase): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(ProductListViewModel::class.java) -> ProductListViewModel(menuUseCase) as T
            else -> throw Throwable("Unknown viewmodel class")
        }
    }
}