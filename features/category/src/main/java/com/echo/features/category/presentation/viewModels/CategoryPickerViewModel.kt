package com.echo.features.category.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.echo.features.category.data.models.CategoryPreferenceDto
import com.echo.features.category.data.models.PreferenceType
import com.echo.features.category.domain.useCases.GetCategoriesUseCase
import com.echo.features.category.domain.useCases.SetPreferenceUseCase
import com.echo.features.category.presentation.states.CategoryPickerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryPickerViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val setPreferenceUseCase: SetPreferenceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryPickerUiState>(CategoryPickerUiState.Loading)
    val uiState: StateFlow<CategoryPickerUiState> = _uiState.asStateFlow()

    init{
        loadCategories()
    }

    fun loadCategories(){
        viewModelScope.launch {
            _uiState.value = CategoryPickerUiState.Loading
            getCategoriesUseCase()
                .onSuccess { categories ->
                    _uiState.value = CategoryPickerUiState.Content(categories)
                }
                .onFailure { error ->
                    _uiState.value = CategoryPickerUiState.Error(
                        error.message ?: "Jib,rf pfuheprb"
                    )
                }
        }
    }

    fun onPreferenceClick(category: CategoryPreferenceDto){
        val nextPreference = when (category.preferenceType) {
            PreferenceType.NEUTRAL -> PreferenceType.LIKE
            PreferenceType.LIKE -> PreferenceType.HATE
            PreferenceType.HATE -> PreferenceType.NEUTRAL
            else -> PreferenceType.NEUTRAL
        }

        updateCategoryInState(category.copy(preferenceType = nextPreference))

        viewModelScope.launch {
            setPreferenceUseCase(
                category = category,
                newPreference = nextPreference
                )
                .onFailure {
                    error ->
                    updateCategoryInState(category)
                    _uiState.value = CategoryPickerUiState.Error(
                        error.message ?: "Ошибка сохранения"
                    )
                }
        }
    }

    private fun updateCategoryInState(updatedCategory: CategoryPreferenceDto) {
        val currentState = _uiState.value
        if (currentState is CategoryPickerUiState.Content) {
            val updatedList = currentState.categories.map {
                if (it.id == updatedCategory.id) updatedCategory else it
            }
            _uiState.value = CategoryPickerUiState.Content(updatedList)
        }
    }
}