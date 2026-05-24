package com.echo.features.category.di

import com.echo.features.category.api.CategoryApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CategoryNetworkModule {
    @Provides
    @CategoryScope
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi =
        retrofit.create<CategoryApi>(CategoryApi::class.java)
}