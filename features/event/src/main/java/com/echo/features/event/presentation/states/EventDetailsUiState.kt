package com.echo.features.event.presentation.states

import com.echo.features.event.data.models.FullEventDto

sealed class EventDetailsUiState {
    object Loading : EventDetailsUiState()
    data class Content(val event: FullEventDto) : EventDetailsUiState()
    data class Error(val message: String) : EventDetailsUiState()
}