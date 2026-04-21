package com.echo.features.feed.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.echo.features.feed.domain.repositories.FeedRepository
import com.echo.features.feed.domain.useCases.GetFeedUseCase
import com.echo.features.feed.presentation.states.FeedUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val repository: FeedRepository,
    private val useCase: GetFeedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init{
        loadFeed()
    }

    fun loadFeed() {
        viewModelScope.launch {
            _uiState.value = FeedUiState.Loading

            val result = useCase.invoke()

            result.onSuccess {
                fullFeed ->
                _uiState.value = FeedUiState.Content(feed = fullFeed)
            }.onFailure {
                error ->
                _uiState.value = FeedUiState.Error(
                    message = error.message ?: "Ошибка загрузки"
                )
            }

        }
    }

}