package com.echo.features.friends.di

import com.echo.features.friends.api.FriendsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class FriendsNetworkModule {
    @Provides
    @FriendsScope
    fun provideFriendsApi(retrofit: Retrofit): FriendsApi =
        retrofit.create(FriendsApi::class.java)
}