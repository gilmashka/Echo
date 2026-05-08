package com.echo.features.event.di

import com.echo.features.event.api.EventDetailsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class EventDetailsNetworkModule {
    @Provides
    @EventDetailsScope
    fun provideEventDetailsApi(retrofit: Retrofit): EventDetailsApi =
        retrofit.create(EventDetailsApi::class.java)
}