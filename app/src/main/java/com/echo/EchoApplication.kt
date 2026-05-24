package com.echo

import android.app.Application
import com.echo.core.network.di.AppComponent
import com.echo.core.network.di.DaggerAppComponent

class EchoApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}