package com.mirfanrafif.siwarung.core.data.local

import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.local.entities.WarungEntity
import com.mirfanrafif.siwarung.core.data.local.preferences.IUserPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor(private val preferences: IUserPreferences) {
    fun setUser(user: UserEntity) = preferences.setUser(user)
    fun getToken(): String = preferences.getToken()
    fun setToken(token: String) = preferences.setToken(token)
    fun getWarung(): WarungEntity = preferences.getUser().warung
}