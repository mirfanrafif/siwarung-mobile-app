package com.mirfanrafif.siwarung.di

import com.mirfanrafif.siwarung.core.di.CoreComponent
import com.mirfanrafif.siwarung.core.di.MenuModule
import com.mirfanrafif.siwarung.core.domain.menu.MenuInteractor
import com.mirfanrafif.siwarung.core.domain.menu.MenuUseCase
import com.mirfanrafif.siwarung.core.repository.IMenuRepository
import com.mirfanrafif.siwarung.core.repository.MenuRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class AppModule {
    @Binds
    abstract fun provideMenuUseCase(menuInteractor: MenuInteractor): MenuUseCase
}