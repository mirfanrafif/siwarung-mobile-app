package com.mirfanrafif.siwarung.core.di

import com.mirfanrafif.siwarung.core.data.local.LocalModule
import com.mirfanrafif.siwarung.core.data.local.UserLocalDataSource
import com.mirfanrafif.siwarung.core.data.remote.RemoteModule
import com.mirfanrafif.siwarung.core.data.remote.UserRemoteDataSource
import com.mirfanrafif.siwarung.core.repository.IUserRepository
import com.mirfanrafif.siwarung.core.repository.UserRepository
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        RemoteModule::class,
        LocalModule::class
    ]
)
class UserModule {
    @Provides
    fun provideUserRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource
    ): IUserRepository = UserRepository(userRemoteDataSource, userLocalDataSource)
}