package com.echo.features.auth.di

import com.echo.features.auth.presentation.viewModels.AuthViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, NetworkModule::class])
interface AuthComponent {

    fun getViewModel(): AuthViewModel
}