package com.echo.features.profile.di

import com.echo.features.profile.data.repositoryImpls.ProfileRepositoryImpl
import com.echo.features.profile.domain.repositories.ProfileRepository
import dagger.Binds
import dagger.Module

@Module
interface ProfileRepositoryModule {

    @Binds
    fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository
}