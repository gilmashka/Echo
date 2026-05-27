package com.echo.features.friends.domain.useCases

import com.echo.features.friends.data.models.FriendshipRequestDto
import com.echo.features.friends.domain.repositories.FriendsRepository
import javax.inject.Inject

class GetIncomingRequestsUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(): Result<List<FriendshipRequestDto>> = repository.getIncomingRequests()
}