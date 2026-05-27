package com.echo.features.friends.domain.useCases

import com.echo.core.network.models.FriendDto
import com.echo.features.friends.domain.repositories.FriendsRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(page: Int = 0, size: Int = 20): Result<List<FriendDto>> = repository.getAllUsers(page, size)
}