package com.echo.features.friends.domain.repositories

import com.echo.core.network.models.FriendDto
import com.echo.features.friends.data.models.FriendshipRequestDto
import com.echo.features.friends.data.models.FriendshipRequestForm

interface FriendsRepository {
    suspend fun getFriends(): Result<List<FriendDto>>
    suspend fun getIncomingRequests(): Result<List<FriendshipRequestDto>>
    suspend fun getOutcomingRequests(): Result<List<FriendshipRequestDto>>
    suspend fun sendRequest(form: FriendshipRequestForm): Result<Unit>
    suspend fun searchUsers(query: String): Result<List<FriendDto>>
    suspend fun getAllUsers(page: Int, size: Int): Result<List<FriendDto>>
}