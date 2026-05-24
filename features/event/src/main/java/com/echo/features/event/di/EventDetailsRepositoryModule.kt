package com.echo.features.event.di

import com.echo.features.event.data.repositoryImpls.EventDetailsRepositoryImpl
import com.echo.features.event.domain.repositories.EventDetailsRepository
import dagger.Binds
import dagger.Module

@Module
interface EventDetailsRepositoryModule {
    @Binds
    fun bindEventDetailsRepository(impl: EventDetailsRepositoryImpl): EventDetailsRepository
}