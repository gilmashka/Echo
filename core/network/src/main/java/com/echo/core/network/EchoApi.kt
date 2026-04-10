package com.echo.core.network

import com.echo.core.network.models.UserForm
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
interface EchoApi {

    @POST("register")
    suspend fun register(@Body form:UserForm): Response<Long>

    @POST("login")
    suspend fun login(): Response<Long>

}
