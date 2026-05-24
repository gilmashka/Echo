package com.echo.features.feed.domain.useCases

import com.echo.features.feed.domain.models.FullFeed
import com.echo.features.feed.domain.repositories.FeedRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetFeedUseCase @Inject constructor(
    private val repository: FeedRepository
) {

    suspend operator fun invoke(): Result<FullFeed>  = coroutineScope {

        try{

            val favouriteDeffered = async { repository.getFavouriteFeed() }
            val neutralDeffered = async { repository.getNeutralFeed() }

            val favouriteResult = favouriteDeffered.await()
            val neutralResult = neutralDeffered.await()

            if(favouriteResult.isFailure || neutralResult.isFailure){
                return@coroutineScope Result.failure(Exception("Ошибка загрузки"))
            }

            val favouriteFeed = favouriteResult.getOrDefault(emptyList())
            val neutralFeed = neutralResult.getOrDefault(emptyList())
            val showPlaceholder = favouriteFeed.isEmpty()

            Result.success(
                FullFeed(
                    favouriteFeed = favouriteFeed,
                    neutralFeed = neutralFeed,
                    showPlaceholder = showPlaceholder
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}