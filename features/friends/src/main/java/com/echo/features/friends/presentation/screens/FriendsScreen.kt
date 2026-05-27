package com.echo.features.friends.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.echo.core.network.di.NetworkModule
import com.echo.core.uikit.components.EchoDivider
import com.echo.core.uikit.components.EchoPrimaryButton
import com.echo.core.uikit.components.EchoPrimaryIconButton
import com.echo.core.uikit.components.EchoSearchField
import com.echo.core.uikit.components.EchoSecondaryButton
import com.echo.core.network.models.FriendDto
import com.echo.features.friends.data.models.FriendshipRequestDto
import com.echo.features.friends.presentation.states.FriendsUiState
import com.echo.features.friends.presentation.viewModels.FriendsViewModel
import com.echo.features.friends.presentation.viewModels.FriendsViewModel.*

@Composable
fun FriendsScreen(viewModel: FriendsViewModel) {
    val state by viewModel.uiState.collectAsState()
    var currentFilter by remember { mutableStateOf(FriendsFilterMode.FRIENDS) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(currentFilter) {
        when (currentFilter) {
            FriendsFilterMode.FRIENDS -> viewModel.loadFriends()
            FriendsFilterMode.INCOMING -> viewModel.loadIncomingRequests()
            FriendsFilterMode.OUTCOMING -> viewModel.loadOutcomingRequests()
            FriendsFilterMode.SEARCH -> { }
            FriendsFilterMode.ALL_USERS -> viewModel.loadAllUsers()
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Друзья",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(15.dp))
            EchoDivider()
            Spacer(Modifier.height(15.dp))

            EchoSearchField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                onSearchClick = {
                    currentFilter = FriendsFilterMode.SEARCH
                    viewModel.searchUsers(searchQuery)
                },
                placeholder = "Поиск пользователей",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                FriendsFilterChip("Друзья", currentFilter == FriendsFilterMode.FRIENDS) {
                    currentFilter = FriendsFilterMode.FRIENDS
                }
                Spacer(Modifier.width(8.dp))
                FriendsFilterChip("Входящие", currentFilter == FriendsFilterMode.INCOMING) {
                    currentFilter = FriendsFilterMode.INCOMING
                }
                Spacer(Modifier.width(8.dp))
                FriendsFilterChip("Исходящие", currentFilter == FriendsFilterMode.OUTCOMING) {
                    currentFilter = FriendsFilterMode.OUTCOMING
                }
                Spacer(Modifier.width(8.dp))
                FriendsFilterChip("Все", currentFilter == FriendsFilterMode.ALL_USERS) {
                    currentFilter = FriendsFilterMode.ALL_USERS
                    viewModel.loadAllUsers()
                }
            }

            Spacer(Modifier.height(16.dp))

            when (val currentState = state) {
                is FriendsUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is FriendsUiState.Error -> Text(currentState.message, color = MaterialTheme.colorScheme.error)
                is FriendsUiState.FriendsList -> FriendsList(currentState.friends, viewModel)
                is FriendsUiState.FriendshipRequests -> RequestsList(currentState.requests, currentState.isIncoming, viewModel)
                is FriendsUiState.SearchResults -> SearchResultsList(currentState.users, viewModel)
                is FriendsUiState.AllUsers -> SearchResultsList(currentState.users, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FriendsFilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(selected = selected, onClick = onClick, label = { Text(label, style = MaterialTheme.typography.displaySmall) })
}

@Composable
private fun FriendsList(
    friends: List<FriendDto>,
    viewModel: FriendsViewModel
) {
    if (friends.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("У вас пока нет друзей", style = MaterialTheme.typography.displaySmall)
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(friends) { friend ->
                FriendCard(
                    friend = friend,
                    onRemove = { viewModel.removeFriend(friend.id) }
                )
            }
        }
    }
}

@Composable
private fun FriendCard(
    friend: FriendDto,
    onRemove: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            val avatarUrl = friend.avatarPath?.let { NetworkModule.UPLOADS_URL + it }
            Box(
                modifier = Modifier.size(48.dp).clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                if (avatarUrl != null) {
                    AsyncImage(model = avatarUrl, contentDescription = null,
                        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                } else {
                    Icon(
                        Icons.Rounded.Person, null, Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
            Spacer(Modifier.width(12.dp))
            Text(friend.nickname, style = MaterialTheme.typography.displayMedium)

            Spacer(Modifier.width(35.dp))

            EchoPrimaryButton(
                text = "Удалить",
                onClick = onRemove,
                isDangerous = true
            )
        }
    }
}


@Composable
private fun RequestsList(
    requests: List<FriendshipRequestDto>,
    isIncoming: Boolean,
    viewModel: FriendsViewModel
) {
    if (requests.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                if (isIncoming) "Нет входящих заявок" else "Нет исходящих заявок",
                style = MaterialTheme.typography.displaySmall
            )
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(requests) { request ->
                RequestCard(request = request, isIncoming = isIncoming, viewModel = viewModel)
            }
        }
    }
}

@Composable
private fun RequestCard(
    request: FriendshipRequestDto,
    isIncoming: Boolean,
    viewModel: FriendsViewModel
) {
    val friend = request.friendInfo

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val avatarUrl = friend.avatarPath?.let { NetworkModule.UPLOADS_URL + it }
            Box(
                modifier = Modifier.size(48.dp).clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                if (avatarUrl != null) {
                    AsyncImage(model = avatarUrl, contentDescription = null,
                        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                } else {
                    Icon(Icons.Rounded.Person, null, Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
            Spacer(Modifier.width(12.dp))
            Text(friend.nickname, style = MaterialTheme.typography.displayMedium, modifier = Modifier.weight(1f))

            if (isIncoming) {
                EchoPrimaryIconButton(
                    icon = Icons.Rounded.Check,
                    onClick = { viewModel.acceptRequest(friend.id) },
                    modifier = Modifier.size(40.dp),
                    contentDescription = "Принять"
                )
                Spacer(Modifier.width(8.dp))
                EchoPrimaryIconButton(
                    icon = Icons.Rounded.Close,
                    onClick = { viewModel.declineRequest(friend.id) },
                    modifier = Modifier.size(40.dp),
                    contentDescription = "Отклонить",
                    isDangerous = true
                )
            } else {
                EchoPrimaryIconButton(
                    icon = Icons.Rounded.Close,
                    onClick = { viewModel.declineRequest(friend.id) },
                    modifier = Modifier.size(40.dp),
                    contentDescription = "Отменить",
                    isDangerous = true
                )
            }
        }
    }
}

@Composable
private fun SearchResultsList(
    users: List<FriendDto>,
    viewModel: FriendsViewModel
) {
    if (users.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Ничего не найдено", style = MaterialTheme.typography.displaySmall)
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(users) { user ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val avatarUrl = user.avatarPath?.let { NetworkModule.UPLOADS_URL + it }
                        Box(
                            modifier = Modifier.size(48.dp).clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            if (avatarUrl != null) {
                                AsyncImage(model = avatarUrl, contentDescription = null,
                                    modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                            } else {
                                Icon(Icons.Rounded.Person, null, Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer)
                            }
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(user.nickname, style = MaterialTheme.typography.displaySmall, modifier = Modifier.weight(1f))
                        EchoPrimaryButton(text = "Добавить", onClick = { viewModel.sendRequest(user.id) })
                    }
                }
            }
        }
    }
}