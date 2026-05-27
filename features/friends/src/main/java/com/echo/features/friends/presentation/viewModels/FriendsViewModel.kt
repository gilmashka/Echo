package com.echo.features.friends.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.echo.features.friends.data.models.FriendshipRequestForm
import com.echo.features.friends.domain.useCases.*
import com.echo.features.friends.presentation.states.FriendsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val getFriendsUseCase: GetFriendsUseCase,
    private val getIncomingRequestsUseCase: GetIncomingRequestsUseCase,
    private val getOutcomingRequestsUseCase: GetOutcomingRequestsUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
    private val searchUsersUseCase: SearchUsersUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FriendsUiState>(FriendsUiState.Loading)
    val uiState: StateFlow<FriendsUiState> = _uiState.asStateFlow()


    enum class FriendsFilterMode { FRIENDS, INCOMING, OUTCOMING, SEARCH, ALL_USERS }


    fun loadFriends() {
        viewModelScope.launch {
            _uiState.value = FriendsUiState.Loading
            getFriendsUseCase()
                .onSuccess { _uiState.value = FriendsUiState.FriendsList(it) }
                .onFailure { _uiState.value = FriendsUiState.Error(it.message ?: "Ошибка") }
        }
    }

    fun loadIncomingRequests() {
        viewModelScope.launch {
            _uiState.value = FriendsUiState.Loading
            getIncomingRequestsUseCase()
                .onSuccess { _uiState.value = FriendsUiState.FriendshipRequests(it, isIncoming = true) }
                .onFailure { _uiState.value = FriendsUiState.Error(it.message ?: "Ошибка") }
        }
    }

    fun loadOutcomingRequests() {
        viewModelScope.launch {
            _uiState.value = FriendsUiState.Loading
            getOutcomingRequestsUseCase()
                .onSuccess { _uiState.value = FriendsUiState.FriendshipRequests(it, isIncoming = false) }
                .onFailure { _uiState.value = FriendsUiState.Error(it.message ?: "Ошибка") }
        }
    }

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _uiState.value = FriendsUiState.Loading
            searchUsersUseCase(query)
                .onSuccess { _uiState.value = FriendsUiState.SearchResults(it) }
                .onFailure { _uiState.value = FriendsUiState.Error(it.message ?: "Ошибка") }
        }
    }

    fun loadAllUsers() {
        viewModelScope.launch {
            _uiState.value = FriendsUiState.Loading
            getAllUsersUseCase()
                .onSuccess { _uiState.value = FriendsUiState.AllUsers(it) }
                .onFailure { _uiState.value = FriendsUiState.Error(it.message ?: "Ошибка") }
        }
    }

    fun sendRequest(recipientId: Long) {
        viewModelScope.launch {
            sendFriendRequestUseCase(FriendshipRequestForm(recipientId, "SENT"))
                .onSuccess {
                    loadAllUsers()
                }
        }
    }

    fun acceptRequest(recipientId: Long) {
        viewModelScope.launch {
            sendFriendRequestUseCase(FriendshipRequestForm(recipientId, "ACCEPTED"))
            loadIncomingRequests()
        }
    }

    fun declineRequest(recipientId: Long) {
        viewModelScope.launch {
            sendFriendRequestUseCase(FriendshipRequestForm(recipientId, "DECLINED"))
            loadIncomingRequests()
        }
    }

    fun removeFriend(friendId: Long) {
        viewModelScope.launch {
            sendFriendRequestUseCase(FriendshipRequestForm(friendId, "DECLINED"))
            loadFriends()
        }
    }
}