package com.mirfanrafif.siwarung.domain.di

import com.mirfanrafif.siwarung.domain.usecases.menu.MenuInteractor
import com.mirfanrafif.siwarung.domain.usecases.menu.MenuUseCase
import com.mirfanrafif.siwarung.domain.usecases.user.getsession.GetSessionInteractor
import com.mirfanrafif.siwarung.domain.usecases.user.getsession.GetSessionUseCase
import com.mirfanrafif.siwarung.domain.usecases.user.login.LoginInteractor
import com.mirfanrafif.siwarung.domain.usecases.user.login.LoginUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {
    @Binds
    abstract fun provideMenuUseCase(menuInteractor: MenuInteractor): MenuUseCase

    @Binds
    abstract fun provideGetSessionUseCase(getsessionInteractor: GetSessionInteractor): GetSessionUseCase

    @Binds
    abstract fun provideLoginUseCase(loginInteractor: LoginInteractor): LoginUseCase
}