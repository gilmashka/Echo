package com.echo.features.feed.api

import com.echo.features.feed.data.models.ShortEventDto
import retrofit2.Response
import retrofit2.http.GET

interface FeedApi {

    @GET("feed/favourite")
    suspend fun getFavouriteFeed(): Response<List<ShortEventDto>>

    @GET("feed/neutral")
    suspend fun getNeutralFeed(): Response<List<ShortEventDto>>
}