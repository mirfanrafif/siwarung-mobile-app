package com.mirfanrafif.siwarung

import android.app.Application
import com.mirfanrafif.siwarung.core.di.CoreComponent
import com.mirfanrafif.siwarung.core.di.DaggerCoreComponent

open class SiwarungApp: Application() {
    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}