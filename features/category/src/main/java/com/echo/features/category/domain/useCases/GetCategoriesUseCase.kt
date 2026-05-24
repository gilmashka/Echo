package com.echo.features.category.domain.useCases

import com.echo.features.category.data.models.CategoryPreferenceDto
import com.echo.features.category.domain.repositories.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(): Result<List<CategoryPreferenceDto>> {
        return repository.getCategories()
    }
}