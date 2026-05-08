package com.echo.features.feed.presentation.states

import com.echo.features.feed.data.models.ShortEventDto
import com.echo.features.feed.domain.models.FullFeed

sealed class FeedUiState {

    object Loading : FeedUiState()

    data class Content(
        val feed : FullFeed,
        val isRefreshing: Boolean = false
    ) : FeedUiState()

    data class Error (val message: String) : FeedUiState()
}