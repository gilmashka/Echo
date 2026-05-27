package com.echo.features.feed.domain.repositories

import com.echo.features.feed.data.models.ShortEventDto

interface FeedRepository {

    suspend fun getFavouriteFeed() : Result<List<ShortEventDto>>

    suspend fun getNeutralFeed() : Result<List<ShortEventDto>>
    suspend fun getLikedEvents(): Result<List<ShortEventDto>>
    suspend fun getDislikedEvents(): Result<List<ShortEventDto>>
    suspend fun getFriendsFeed(): Result<List<ShortEventDto>>
}