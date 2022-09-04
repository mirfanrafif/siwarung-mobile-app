package com.mirfanrafif.siwarung.core.data.remote

import com.google.gson.Gson
import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import com.mirfanrafif.siwarung.core.data.remote.responses.ApiErrorResponse
import com.mirfanrafif.siwarung.core.data.remote.responses.ApiResponse
import com.mirfanrafif.siwarung.core.data.remote.responses.LoginResponseData
import com.mirfanrafif.siwarung.core.data.remote.services.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(private val userService: UserService) {
    fun login(request: LoginRequest): Flow<ApiResponse<LoginResponseData>> = flow {
        try {
            var response = userService.login(request)
            if(response.data != null) {
                emit(ApiResponse.success(response.data!!))
            }else{
                emit(ApiResponse.empty("Gagal login", LoginResponseData()))
            }
        }catch (e: HttpException) {
            var errorBody = e.response()?.errorBody()?.string()
            var message = Gson().fromJson(errorBody, ApiErrorResponse::class.java)
            emit(ApiResponse.error("Gagal login: " + message.message, LoginResponseData()))
        }
    }.flowOn(Dispatchers.IO)
}