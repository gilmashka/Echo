package com.echo.core.network.di

import android.content.Context
import com.echo.core.network.storage.AuthStorage
import com.echo.core.network.storage.SharedPreferencesAuthStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    @Singleton
    fun provideAuthStorage(context: Context): AuthStorage {
        return SharedPreferencesAuthStorage(context)
    }
}
