package com.echo.features.category.di

import android.content.Context
import androidx.room.Room
import com.echo.features.category.data.local.CategoryDao
import com.echo.features.category.data.local.CategoryDatabase
import dagger.Module
import dagger.Provides

@Module
class CategoryDatabaseModule {
    @Provides
    @CategoryScope
    fun provideDatabase(context: Context): CategoryDatabase =
        Room.databaseBuilder(context, CategoryDatabase::class.java, "echo.db").build()

    @Provides
    @CategoryScope
    fun provideDao(db: CategoryDatabase): CategoryDao = db.categoryDao()
}