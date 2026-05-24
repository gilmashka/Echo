package com.echo.features.event.api

import com.echo.features.event.data.models.FullEventDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EventDetailsApi {

    @GET("feed/details/{eventId}")
    suspend fun getEventDetails(
        @Path("eventId") eventId: Int
    ): Response<FullEventDto>
}