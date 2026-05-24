package com.echo.features.category.api

import com.echo.features.category.data.models.CategoryPreferenceDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoryApi {

    @GET("categories")
    suspend fun getUserCategoryList(): Response<List<CategoryPreferenceDto>>

    @POST("categories/preference")
    suspend fun setUserCategoryPreference(
        @Body dto: CategoryPreferenceDto
    ): Response<Unit>
}