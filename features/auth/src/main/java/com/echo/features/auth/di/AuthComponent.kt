package com.echo.features.auth.di

import com.echo.core.network.di.AppComponent
import com.echo.features.auth.presentation.viewModels.AuthViewModel
import dagger.Component

@AuthScope
@Component(
    dependencies = [AppComponent::class],
    modules = [AuthRepositoryModule::class]
)
interface AuthComponent {

    fun getViewModel(): AuthViewModel

    @Component.Builder
    interface Builder {
        fun appComponent(component: AppComponent): Builder
        fun build(): AuthComponent
    }
}