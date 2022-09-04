package com.mirfanrafif.siwarung.core.domain.usecases.user.login

import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import com.mirfanrafif.siwarung.core.repository.Resource
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    fun login(request: LoginRequest): Flow<Resource<UserEntity?>>
}