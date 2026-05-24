package com.echo.features.event.domain.useCases

import com.echo.features.event.data.models.FullEventDto
import com.echo.features.event.domain.repositories.EventDetailsRepository
import javax.inject.Inject

class GetEventDetailsUseCase @Inject constructor(
    private val repository: EventDetailsRepository
) {
    suspend operator fun invoke(eventId: Int): Result<FullEventDto> {
        return repository.getEventDetails(eventId)
    }
}