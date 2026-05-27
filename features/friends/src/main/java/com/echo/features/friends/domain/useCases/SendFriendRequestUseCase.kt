package com.echo.features.friends.domain.useCases

import com.echo.features.friends.data.models.FriendshipRequestForm
import com.echo.features.friends.domain.repositories.FriendsRepository
import javax.inject.Inject

class SendFriendRequestUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(form: FriendshipRequestForm): Result<Unit> = repository.sendRequest(form)
}