package com.mirfanrafif.siwarung.core.data.local

import android.content.Context
import com.mirfanrafif.siwarung.core.data.local.preferences.IUserPreferences
import com.mirfanrafif.siwarung.core.data.local.preferences.UserPreferences
import dagger.Module
import dagger.Provides

@Module
class LocalModule {
    @Provides
    fun provieUserPreferences(context: Context): IUserPreferences = UserPreferences(context)
}