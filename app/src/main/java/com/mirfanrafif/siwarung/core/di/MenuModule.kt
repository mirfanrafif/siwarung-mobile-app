package com.mirfanrafif.siwarung.core.di

import com.mirfanrafif.siwarung.core.data.remote.MenuRemoteDataSource
import com.mirfanrafif.siwarung.core.data.remote.RemoteModule
import com.mirfanrafif.siwarung.core.repository.IMenuRepository
import com.mirfanrafif.siwarung.core.repository.MenuRepository
import dagger.Module
import dagger.Provides

@Module(includes = [
    RemoteModule::class
])
class MenuModule {
    @Provides
    fun provideMenuRepository(remoteDataSource: MenuRemoteDataSource): IMenuRepository = MenuRepository(remoteDataSource)
}