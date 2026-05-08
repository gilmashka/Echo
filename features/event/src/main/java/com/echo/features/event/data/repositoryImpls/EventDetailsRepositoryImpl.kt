package com.echo.features.event.data.repositoryImpls

import android.util.Log
import com.echo.features.event.api.EventDetailsApi
import com.echo.features.event.data.models.FullEventDto
import com.echo.features.event.domain.repositories.EventDetailsRepository
import javax.inject.Inject

class EventDetailsRepositoryImpl @Inject constructor(
    private val api: EventDetailsApi
) : EventDetailsRepository {

    override suspend fun getEventDetails(eventId: Int): Result<FullEventDto> {
        return try {
            Log.d("EventDetails", "Requesting eventId: $eventId")
            val response = api.getEventDetails(eventId)
            Log.d("EventDetails", "Response code: ${response.code()}")
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else if (response.code() == 404) {
                Result.failure(Exception("Событие не найдено"))
            } else {
                Result.failure(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}