package com.echo.features.friends.data.repositoryImpls

import com.echo.features.friends.api.FriendsApi
import com.echo.core.network.models.FriendDto
import com.echo.features.friends.data.models.FriendshipRequestDto
import com.echo.features.friends.data.models.FriendshipRequestForm
import com.echo.features.friends.domain.repositories.FriendsRepository
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val api: FriendsApi
) : FriendsRepository {

    override suspend fun getFriends(): Result<List<FriendDto>> {
        return try {
            val response = api.getFriends()
            if (response.isSuccessful) Result.success(response.body() ?: emptyList())
            else Result.failure(Exception("Ошибка: ${response.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun getIncomingRequests(): Result<List<FriendshipRequestDto>> {
        return try {
            val response = api.getIncomingRequests()
            if (response.isSuccessful) Result.success(response.body() ?: emptyList())
            else Result.failure(Exception("Ошибка: ${response.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun getOutcomingRequests(): Result<List<FriendshipRequestDto>> {
        return try {
            val response = api.getOutcomingRequests()
            if (response.isSuccessful) Result.success(response.body() ?: emptyList())
            else Result.failure(Exception("Ошибка: ${response.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun sendRequest(form: FriendshipRequestForm): Result<Unit> {
        return try {
            val response = api.sendRequest(form)
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Ошибка: ${response.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun searchUsers(query: String): Result<List<FriendDto>> {
        return try {
            val response = api.searchUsers(query)
            if (response.isSuccessful) Result.success(response.body() ?: emptyList())
            else Result.failure(Exception("Ошибка: ${response.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun getAllUsers(page: Int, size: Int): Result<List<FriendDto>> {
        return try {
            val response = api.getAllUsers(page, size)
            if (response.isSuccessful) Result.success(response.body() ?: emptyList())
            else Result.failure(Exception("Ошибка: ${response.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }
}