package com.echo.core.network

import com.echo.core.network.models.UserForm
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EchoApi {

    @POST("register")
    suspend fun register(@Body form:UserForm): Response<Long>

    @POST("login")
    suspend fun login(@Body form: UserForm): Response<Long>

    @POST("noted/create/{eventId}")
    suspend fun noteEvent(
        @Path("eventId") eventId: Int,
        @Query("noteType") noteType: String
    ): Response<Unit>

    @POST("noted/delete/{eventId}")
    suspend fun deleteNotedEvent(
        @Path("eventId") eventId: Int
    ): Response<Unit>

    @GET("check")
    suspend fun checkAuth(): Response<Boolean>
}
