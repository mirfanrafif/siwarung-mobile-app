package com.mirfanrafif.siwarung.core.di

import android.content.Context
import com.mirfanrafif.siwarung.core.repository.IMenuRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    MenuModule::class,
])
interface CoreComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideMenuRepository(): IMenuRepository
}