package com.echo.features.friends.di

import com.echo.features.friends.data.repositoryImpls.FriendsRepositoryImpl
import com.echo.features.friends.domain.repositories.FriendsRepository
import dagger.Binds
import dagger.Module

@Module
interface FriendsRepositoryModule {
    @Binds
    fun bindFriendsRepository(impl: FriendsRepositoryImpl): FriendsRepository
}