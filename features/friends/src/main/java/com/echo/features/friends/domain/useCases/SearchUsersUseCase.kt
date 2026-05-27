package com.echo.features.friends.domain.useCases

import com.echo.core.network.models.FriendDto
import com.echo.features.friends.domain.repositories.FriendsRepository
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(query: String): Result<List<FriendDto>> = repository.searchUsers(query)
}