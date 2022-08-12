package com.mirfanrafif.siwarung.domain.usecases.user.getsession

import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.local.entities.WarungEntity
import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import com.mirfanrafif.siwarung.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetSessionUseCase {
    fun checkSession(): Boolean
    fun getWarung(): WarungEntity
}