package com.mirfanrafif.siwarung.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import com.mirfanrafif.siwarung.core.domain.user.UserUseCase
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    fun login(username: String, password: String): LiveData<Resource<UserEntity?>> {
        val request = LoginRequest(username, password)
        return userUseCase.login(request).asLiveData()
    }

    fun checkSession() = userUseCase.checkSession()
}