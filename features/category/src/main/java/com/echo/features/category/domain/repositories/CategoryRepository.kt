package com.echo.features.category.domain.repositories

import com.echo.features.category.data.models.CategoryPreferenceDto

interface CategoryRepository {

    suspend fun getCategories(): Result<List<CategoryPreferenceDto>>
    suspend fun setPreference(dto: CategoryPreferenceDto): Result<Unit>
}