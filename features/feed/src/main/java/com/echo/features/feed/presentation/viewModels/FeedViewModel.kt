package com.echo.features.feed.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.echo.features.feed.domain.useCases.GetDislikedEventsUseCase
import com.echo.features.feed.domain.useCases.GetFeedUseCase
import com.echo.features.feed.domain.useCases.GetLikedEventsUseCase
import com.echo.features.feed.presentation.states.FeedUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val getFeedUseCase: GetFeedUseCase,
    private val getLikedEventsUseCase: GetLikedEventsUseCase,
    private val getDislikedEventsUseCase: GetDislikedEventsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    private val _filterMode = MutableStateFlow(FeedFilterMode.ALL)
    val filterMode: StateFlow<FeedFilterMode> = _filterMode.asStateFlow()

    enum class FeedFilterMode { ALL, LIKED, DISLIKED }

    fun setFilterMode(mode: FeedFilterMode) {
        _filterMode.value = mode
        when (mode) {
            FeedFilterMode.ALL -> loadFeed()
            FeedFilterMode.LIKED -> loadLikedEvents()
            FeedFilterMode.DISLIKED -> loadDislikedEvents()
        }
    }

    init{
        loadFeed()
    }

    fun loadFeed() {
        viewModelScope.launch {
            _uiState.value = FeedUiState.Loading
            getFeedUseCase()
                .onSuccess { _uiState.value = FeedUiState.Content(feed = it) }
                .onFailure { _uiState.value = FeedUiState.Error(it.message ?: "Ошибка") }
        }
    }

    private fun loadLikedEvents() {
        viewModelScope.launch {
            _uiState.value = FeedUiState.Loading
            getLikedEventsUseCase()
                .onSuccess { _uiState.value = FeedUiState.Content(feed = it) }
                .onFailure { _uiState.value = FeedUiState.Error(it.message ?: "Ошибка") }
        }
    }

    private fun loadDislikedEvents() {
        viewModelScope.launch {
            _uiState.value = FeedUiState.Loading
            getDislikedEventsUseCase()
                .onSuccess { _uiState.value = FeedUiState.Content(feed = it) }
                .onFailure { _uiState.value = FeedUiState.Error(it.message ?: "Ошибка") }
        }
    }

    fun refreshFeed() {
        val current = _uiState.value
        if (current is FeedUiState.Content) _uiState.value = current.copy(isRefreshing = true)
        viewModelScope.launch {
            val result = when (_filterMode.value) {
                FeedFilterMode.ALL -> getFeedUseCase()
                FeedFilterMode.LIKED -> getLikedEventsUseCase()
                FeedFilterMode.DISLIKED -> getDislikedEventsUseCase()
            }
            result
                .onSuccess { _uiState.value = FeedUiState.Content(feed = it) }
                .onFailure { _uiState.value = FeedUiState.Error(it.message ?: "Ошибка") }
        }
    }
}