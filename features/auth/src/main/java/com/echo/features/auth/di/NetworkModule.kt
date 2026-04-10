package com.echo.features.auth.di

import com.echo.core.network.EchoApi
import com.echo.core.network.models.ServiceLocator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideEchoApi() : EchoApi {
        return ServiceLocator.echoApi
    }
}