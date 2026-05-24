package com.echo.features.feed.di

import com.echo.features.feed.data.repositoryImpls.FeedRepositoryImpl
import com.echo.features.feed.domain.repositories.FeedRepository
import dagger.Binds
import dagger.Module

@Module
interface FeedRepositoryModule {

    @Binds
    fun bindFeedRepository(impl: FeedRepositoryImpl): FeedRepository
}