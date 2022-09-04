package com.mirfanrafif.siwarung.core.domain.usecases.user.login

import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import com.mirfanrafif.siwarung.core.repository.IUserRepository
import com.mirfanrafif.siwarung.core.repository.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginInteractor @Inject constructor(private val repository: IUserRepository): LoginUseCase {
    override fun login(request: LoginRequest): Flow<Resource<UserEntity?>> = repository.login(request)
}