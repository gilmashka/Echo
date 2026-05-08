package com.echo.features.event.domain.repositories

import com.echo.features.event.data.models.FullEventDto

interface EventDetailsRepository {
    suspend fun getEventDetails(eventId: Int): Result<FullEventDto>
}