package com.echo.features.category.di

import com.echo.core.network.di.AppComponent
import com.echo.features.category.presentation.viewModels.CategoryPickerViewModel
import dagger.Component

@CategoryScope
@Component(
    dependencies = [AppComponent::class],
    modules = [CategoryNetworkModule::class, CategoryRepositoryModule::class, CategoryDatabaseModule::class]
)
interface CategoryComponent {
    fun getViewModel() : CategoryPickerViewModel

    @Component.Builder
    interface Builder {
        fun appComponent(component: AppComponent): Builder
        fun build(): CategoryComponent
    }
}