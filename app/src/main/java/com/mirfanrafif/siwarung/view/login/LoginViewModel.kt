package com.mirfanrafif.siwarung.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import com.mirfanrafif.siwarung.core.domain.usecases.user.getsession.GetSessionUseCase
import com.mirfanrafif.siwarung.core.domain.usecases.user.login.LoginUseCase
import com.mirfanrafif.siwarung.core.repository.Resource
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getSessionUseCase: GetSessionUseCase
) : ViewModel() {
    fun login(username: String, password: String): LiveData<Resource<UserEntity?>> {
        val request = LoginRequest(username, password)
        return loginUseCase.login(request).asLiveData()
    }

    fun checkSession() = getSessionUseCase.checkSession()
}