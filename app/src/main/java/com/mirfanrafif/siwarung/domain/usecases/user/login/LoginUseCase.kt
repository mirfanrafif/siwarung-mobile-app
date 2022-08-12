package com.mirfanrafif.siwarung.domain.usecases.user.login

import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    fun login(request: LoginRequest): Flow<Resource<UserEntity?>>
}