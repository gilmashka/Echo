package com.echo.features.auth.di

import com.echo.features.auth.data.repositoryImpls.AuthRepositoryImpl
import com.echo.features.auth.domain.repositories.AuthRepository
import dagger.Binds
import dagger.Module

@Module
interface AuthRepositoryModule {

    @Binds
    fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}