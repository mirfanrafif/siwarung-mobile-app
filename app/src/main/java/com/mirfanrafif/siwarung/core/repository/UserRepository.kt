package com.mirfanrafif.siwarung.core.repository

import com.mirfanrafif.siwarung.core.data.local.UserLocalDataSource
import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.local.entities.WarungEntity
import com.mirfanrafif.siwarung.core.data.remote.UserRemoteDataSource
import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import com.mirfanrafif.siwarung.core.data.remote.responses.LoginResponseData
import com.mirfanrafif.siwarung.core.data.remote.responses.StatusResponse
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remote: UserRemoteDataSource,
    private val local: UserLocalDataSource
) : IUserRepository {
    override fun login(request: LoginRequest): Flow<Resource<UserEntity?>> = flow {
        emit(Resource.loading(null))
        val response = remote.login(request).first()
        when (response.status) {
            StatusResponse.SUCCESS -> {
                val warung = WarungEntity(
                    response.body.user?.warung?.id ?: 0,
                    response.body.user?.warung?.name ?: "",
                    response.body.user?.warung?.address ?: ""
                )
                val user = UserEntity(
                    id = response.body.user?.id ?: 0,
                    name = response.body.user?.name ?: "",
                    username = response.body.user?.username ?: "",
                    role = response.body.user?.role ?: "",
                    warung = warung
                )
                local.setUser(user)
                local.setToken(response.body.token ?: "")
                emit(Resource.success(user))
            }
            StatusResponse.EMPTY -> {
                emit(Resource.error(response.message, null))
            }
            StatusResponse.ERROR -> {
                emit(Resource.error(response.message, null))
            }
        }
    }

    override fun checkSession(): Boolean {
        val token = local.getToken()
        return token.isNotBlank()
    }

}