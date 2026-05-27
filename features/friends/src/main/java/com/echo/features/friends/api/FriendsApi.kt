package com.echo.features.friends.api

import com.echo.features.friends.data.models.FriendshipRequestForm
import com.echo.core.network.models.FriendDto
import com.echo.features.friends.data.models.FriendshipRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FriendsApi {

    @GET("friends")
    suspend fun getFriends(): Response<List<FriendDto>>

    @GET("friends/incoming")
    suspend fun getIncomingRequests(): Response<List<FriendshipRequestDto>>  // ← другой тип

    @GET("friends/outcoming")
    suspend fun getOutcomingRequests(): Response<List<FriendshipRequestDto>>  // ← другой тип

    @POST("friends/request")
    suspend fun sendRequest(@Body form: FriendshipRequestForm): Response<Unit>

    @GET("profile/search")
    suspend fun searchUsers(@Query("query") query: String): Response<List<FriendDto>>

    @GET("profile/all")
    suspend fun getAllUsers(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<List<FriendDto>>
}