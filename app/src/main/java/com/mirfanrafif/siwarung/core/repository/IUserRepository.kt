package com.mirfanrafif.siwarung.core.repository

import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import com.mirfanrafif.siwarung.core.data.remote.responses.LoginResponseData
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun login(request: LoginRequest): Flow<Resource<UserEntity?>>
    fun checkSession(): Boolean
}