package com.echo.core.network.di

import android.content.Context
import com.echo.core.network.EchoApi
import com.echo.core.network.storage.AuthStorage
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class,
    StorageModule::class])
interface AppComponent {

    fun echoApi() : EchoApi
    fun authStorage(): AuthStorage

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}