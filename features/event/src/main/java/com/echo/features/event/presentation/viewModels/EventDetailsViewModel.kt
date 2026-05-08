package com.echo.features.event.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.echo.features.event.domain.useCases.GetEventDetailsUseCase
import com.echo.features.event.presentation.states.EventDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventDetailsViewModel @Inject constructor(
    private val getEventDetailsUseCase: GetEventDetailsUseCase
): ViewModel(){

    private val _uiState = MutableStateFlow<EventDetailsUiState>(EventDetailsUiState.Loading)
    val uiState: StateFlow<EventDetailsUiState> = _uiState.asStateFlow()

    fun loadEventDetails(eventId: Int) {
        viewModelScope.launch {
            _uiState.value = EventDetailsUiState.Loading

            getEventDetailsUseCase(eventId)
                .onSuccess { event ->
                    _uiState.value = EventDetailsUiState.Content(event)
                }
                .onFailure { error ->
                    _uiState.value = EventDetailsUiState.Error(
                        error.message ?: "Ошибка загрузки"
                    )
                }
        }
    }
}