package com.echo.features.category.di

import com.echo.features.category.data.repositoryImpls.CategoryRepositoryImpl
import com.echo.features.category.domain.repositories.CategoryRepository
import dagger.Binds
import dagger.Module

@Module
interface CategoryRepositoryModule {
    @Binds
    fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository
}