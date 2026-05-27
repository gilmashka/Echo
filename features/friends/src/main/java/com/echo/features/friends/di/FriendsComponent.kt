package com.echo.features.friends.di

import com.echo.core.network.di.AppComponent
import com.echo.features.friends.presentation.viewModels.FriendsViewModel
import dagger.Component

@FriendsScope
@Component(
    dependencies = [AppComponent::class],
    modules = [FriendsNetworkModule::class, FriendsRepositoryModule::class]
)
interface FriendsComponent {
    fun getViewModel(): FriendsViewModel

    @Component.Builder
    interface Builder {
        fun appComponent(component: AppComponent): Builder
        fun build(): FriendsComponent
    }
}