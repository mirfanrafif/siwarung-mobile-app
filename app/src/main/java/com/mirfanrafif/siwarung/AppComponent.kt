package com.mirfanrafif.siwarung

import com.mirfanrafif.siwarung.core.di.CoreComponent
import com.mirfanrafif.siwarung.di.AppModule
import com.mirfanrafif.siwarung.di.AppScope
import com.mirfanrafif.siwarung.productlist.MainActivity
import com.mirfanrafif.siwarung.productlist.ProductCartFragment
import com.mirfanrafif.siwarung.productlist.ProductListFragment
import dagger.Component

@AppScope
@Component(
    dependencies = [
        CoreComponent::class
    ],
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: ProductListFragment)

    fun inject(fragment: ProductCartFragment)
}