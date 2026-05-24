package com.echo.features.feed.data.repositoryImpls

import androidx.compose.ui.graphics.RectangleShape
import com.echo.features.feed.api.FeedApi
import com.echo.features.feed.data.models.ShortEventDto
import com.echo.features.feed.domain.repositories.FeedRepository
import okhttp3.Response
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val api: FeedApi
) : FeedRepository {

    override suspend fun getFavouriteFeed(): Result<List<ShortEventDto>> {
        return try {
            val response = api.getFavouriteFeed()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else if (response.code() == 404) {
                Result.success(emptyList())
            } else {
                Result.failure(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNeutralFeed(): Result<List<ShortEventDto>> {
        return try {
            val response = api.getNeutralFeed()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else if (response.code() == 404) {
                Result.success(emptyList())
            } else {
                Result.failure(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}