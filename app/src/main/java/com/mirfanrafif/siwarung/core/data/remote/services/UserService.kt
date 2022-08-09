package com.mirfanrafif.siwarung.core.data.remote.services

import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import com.mirfanrafif.siwarung.core.data.remote.responses.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse
}