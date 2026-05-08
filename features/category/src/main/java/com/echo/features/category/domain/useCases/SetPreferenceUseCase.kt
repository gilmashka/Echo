package com.echo.features.category.domain.useCases

import com.echo.features.category.data.models.CategoryPreferenceDto
import com.echo.features.category.data.models.PreferenceType
import com.echo.features.category.domain.repositories.CategoryRepository
import javax.inject.Inject

class SetPreferenceUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(category: CategoryPreferenceDto, newPreference: PreferenceType): Result<Unit> {
        val updatedDto = category.copy(preferenceType = newPreference)
        return repository.setPreference(updatedDto)
    }
}