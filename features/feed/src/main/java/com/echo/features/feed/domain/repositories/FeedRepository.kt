package com.echo.features.feed.domain.repositories

import com.echo.features.feed.data.models.ShortEventDto

interface FeedRepository {

    suspend fun getFavouriteFeed() : Result<List<ShortEventDto>>

    suspend fun getNeutralFeed() : Result<List<ShortEventDto>>
}