package com.echo.features.profile.di

import android.content.Context
import com.echo.features.profile.api.ProfileApi
import com.echo.features.profile.presentation.states.ProfileUiState
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ProfileNetworkModule {

    @Provides
    @ProfileScope
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create<ProfileApi>(ProfileApi::class.java)

}