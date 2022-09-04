package com.mirfanrafif.siwarung.core.domain.usecases.user.getsession

import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.local.entities.WarungEntity
import com.mirfanrafif.siwarung.core.data.remote.requests.LoginRequest
import kotlinx.coroutines.flow.Flow

interface GetSessionUseCase {
    fun checkSession(): Boolean
    fun getWarung(): WarungEntity
}