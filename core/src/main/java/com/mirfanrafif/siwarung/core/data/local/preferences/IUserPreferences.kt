package com.mirfanrafif.siwarung.core.data.local.preferences

import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import javax.inject.Singleton

@Singleton
interface IUserPreferences {
    fun setUser(user: UserEntity)
    fun getUser(): UserEntity
    fun setToken(token: String)
    fun getToken(): String
}