package com.echo.features.profile.api

import com.echo.core.network.models.UserForm
import com.echo.features.profile.data.models.UserDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileApi {

    @GET(value = "profile")
    suspend fun getUserProfile(): Response<UserDto>

    @POST(value = "profile/update")
    suspend fun changeUserProfile(
        @Body form: UserForm): Response<Unit>

    @POST(value = "profile/delete")
    suspend fun deleteUserProfile(): Response<Unit>

    @Multipart
    @POST(value = "profile/avatar/set")
    suspend fun setUserAvatar(
        @Part avatar: MultipartBody.Part
    ): Response<Unit>

    @POST(value = "profile/avatar/delete")
    suspend fun deleteUserAvatar(): Response<Unit>
}