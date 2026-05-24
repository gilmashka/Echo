package com.echo.features.profile.di

import android.content.Context
import com.echo.core.network.di.AppComponent
import com.echo.features.profile.presentation.viewModels.ProfileViewModel
import dagger.BindsInstance
import dagger.Component

@ProfileScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ProfileNetworkModule::class, ProfileRepositoryModule::class]
)
interface ProfileComponent {

    fun getViewModel(): ProfileViewModel

    @Component.Builder
    interface Builder {
        fun appComponent(component: AppComponent): Builder
        fun build(): ProfileComponent
    }
}