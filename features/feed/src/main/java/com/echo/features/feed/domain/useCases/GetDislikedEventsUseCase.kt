package com.echo.features.feed.domain.useCases

import com.echo.features.feed.data.models.ShortEventDto
import com.echo.features.feed.domain.models.FullFeed
import com.echo.features.feed.domain.repositories.FeedRepository
import javax.inject.Inject

class GetDislikedEventsUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(): Result<FullFeed> {
        return repository.getDislikedEvents().map { events ->
            FullFeed(
                favouriteFeed = events,
                neutralFeed = emptyList(),
                showPlaceholder = events.isEmpty()
            )
        }
    }
}