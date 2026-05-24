package com.echo.features.feed.di

import com.echo.features.feed.api.FeedApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class FeedNetworkModule {
    @Provides
    @FeedScope
    fun provideFeedApi(retrofit: Retrofit):
            FeedApi = retrofit.create(FeedApi::class.java)

}