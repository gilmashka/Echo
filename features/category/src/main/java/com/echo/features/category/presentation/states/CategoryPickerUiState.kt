package com.echo.features.category.presentation.states

import com.echo.features.category.data.models.CategoryPreferenceDto

sealed class CategoryPickerUiState {
    object Loading: CategoryPickerUiState()

    data class Content(val categories: List<CategoryPreferenceDto>): CategoryPickerUiState()

    data class Error(val message: String): CategoryPickerUiState()
}