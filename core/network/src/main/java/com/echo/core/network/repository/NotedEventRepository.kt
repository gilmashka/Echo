package com.echo.core.network.repository

import com.echo.core.network.EchoApi
import javax.inject.Inject

class NotedEventRepository @Inject constructor(
    private val api: EchoApi
) {

    suspend fun likeEvent(eventId: Int): Result<Unit> {
        return try {
            val response = api.noteEvent(eventId, "LIKE")
            if (response.isSuccessful) {
                Result.success(Unit)
            }
            else {
                Result.failure(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun dislikeEvent(eventId: Int): Result<Unit> {
        return try {
            val response = api.noteEvent(eventId, "DISLIKE")
            if (response.isSuccessful) {
                Result.success(Unit)
            }
            else {
                Result.failure(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteNotedEvent(eventId: Int): Result<Unit> {
        return try {
            val response = api.deleteNotedEvent(eventId)
            if (response.isSuccessful) {
                Result.success(Unit)
            }
            else {
                Result.failure(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }
}