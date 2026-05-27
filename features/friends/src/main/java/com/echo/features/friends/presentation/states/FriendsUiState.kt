package com.echo.features.friends.presentation.states

import com.echo.core.network.models.FriendDto
import com.echo.features.friends.data.models.FriendshipRequestDto

sealed class FriendsUiState {
    object Loading : FriendsUiState()

    data class FriendsList(val friends: List<FriendDto>) : FriendsUiState()

    data class FriendshipRequests(
        val requests: List<FriendshipRequestDto>,
        val isIncoming: Boolean
    ) : FriendsUiState()

    data class SearchResults(val users: List<FriendDto>) : FriendsUiState()

    data class AllUsers(val users: List<FriendDto>) : FriendsUiState()

    data class Error(val message: String) : FriendsUiState()
}