package com.mirfanrafif.siwarung.core.domain.user

import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun login(request: LoginRequest): Flow<Resource<UserEntity?>>
    fun checkSession(): Boolean
}