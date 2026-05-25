package com.echo.features.event.domain.useCases

import com.echo.core.network.repository.NotedEventRepository
import javax.inject.Inject

class LikeEventUseCase @Inject constructor(
    private val repository: NotedEventRepository
) {
    suspend operator fun invoke(eventId: Int): Result<Unit> = repository.likeEvent(eventId)
}