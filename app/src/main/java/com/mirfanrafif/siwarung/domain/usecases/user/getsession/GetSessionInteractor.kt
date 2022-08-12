package com.mirfanrafif.siwarung.domain.usecases.user.getsession

import com.mirfanrafif.siwarung.core.data.local.entities.WarungEntity
import com.mirfanrafif.siwarung.core.repository.IUserRepository
import javax.inject.Inject

class GetSessionInteractor @Inject constructor (private val repository: IUserRepository) : GetSessionUseCase {
    override fun checkSession(): Boolean  = repository.checkSession()
    override fun getWarung(): WarungEntity = repository.getWarung()
}