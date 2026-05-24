package com.echo.features.event.di

import com.echo.core.network.di.AppComponent
import com.echo.features.event.presentation.viewModels.EventDetailsViewModel
import dagger.Component

@EventDetailsScope
@Component(
    dependencies = [AppComponent::class],
    modules = [EventDetailsNetworkModule::class, EventDetailsRepositoryModule::class]
)
interface EventDetailsComponent {
    fun getViewModel(): EventDetailsViewModel

    @Component.Builder
    interface Builder {
        fun appComponent(component: AppComponent): Builder
        fun build(): EventDetailsComponent
    }
}