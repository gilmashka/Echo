package com.echo.features.feed.di

import androidx.compose.runtime.Composable
import com.echo.core.network.di.AppComponent
import com.echo.features.feed.presentation.viewModels.FeedViewModel
import dagger.Component

@FeedScope
@Component(
    dependencies = [AppComponent::class],
    modules = [FeedNetworkModule::class, FeedRepositoryModule::class]
)
interface FeedComponent {

    fun getViewModel(): FeedViewModel

    @Component.Builder
    interface Builder {
        fun appComponent(component: AppComponent): Builder
        fun build(): FeedComponent
    }
}