package com.mirfanrafif.siwarung.core.data.local.preferences

import android.content.Context
import androidx.core.content.edit
import com.mirfanrafif.siwarung.core.data.local.entities.UserEntity
import com.mirfanrafif.siwarung.core.data.local.entities.WarungEntity
import javax.inject.Inject
import javax.inject.Singleton

class UserPreferences @Inject constructor (context: Context): IUserPreferences {

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val USER_ID = "user_id"
        private const val USER_NAME = "user_name"
        private const val USER_USERNAME = "user_username"
        private const val USER_ROLE = "user_role"

        private const val WARUNG_ID = "warung_id"
        private const val WARUNG_NAME = "warung_name"
        private const val WARUNG_ADDRESS = "warung_address"

        private const val USER_TOKEN = "user_token"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun setUser(user: UserEntity) {
        preferences.edit {
            putInt(USER_ID, user.id)
            putString(USER_NAME, user.name)
            putString(USER_USERNAME, user.username)
            putString(USER_ROLE, user.role)

            putInt(WARUNG_ID, user.warung.id)
            putString(WARUNG_NAME, user.warung.name)
            putString(WARUNG_ADDRESS, user.warung.address)
        }
    }

    override fun getUser(): UserEntity {
        return UserEntity(
            id = preferences.getInt(USER_ID, 0),
            name = preferences.getString(USER_NAME, "") ?: "",
            username = preferences.getString(USER_USERNAME, "") ?: "",
            role = preferences.getString(USER_ROLE, "") ?: "",
            warung = WarungEntity(
                id = preferences.getInt(WARUNG_ID, 0),
                name = preferences.getString(WARUNG_NAME, "") ?: "",
                address = preferences.getString(WARUNG_ADDRESS, "") ?: ""
            )
        )
    }

    override fun setToken(token: String) {
        preferences.edit {
            putString(USER_TOKEN, token)
        }
    }

    override fun getToken(): String {
        return preferences.getString(USER_TOKEN, "") ?: ""
    }
}