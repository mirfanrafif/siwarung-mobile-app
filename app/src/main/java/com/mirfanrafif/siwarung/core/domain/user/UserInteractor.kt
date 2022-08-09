package com.mirfanrafif.siwarung.core.domain.user

import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import com.mirfanrafif.siwarung.core.repository.IUserRepository
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor (private val repository: IUserRepository) : UserUseCase {
    override fun login(request: LoginRequest): Flow<Resource<UserEntity?>> = repository.login(request)
    override fun checkSession(): Boolean  = repository.checkSession()
}