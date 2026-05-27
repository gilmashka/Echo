package com.echo.features.event.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CurrencyRuble
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.ThumbDown
import androidx.compose.material.icons.rounded.ThumbDownOffAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.echo.core.network.di.NetworkModule
import com.echo.core.uikit.components.EchoBottomSheet
import com.echo.core.uikit.components.EchoDivider
import com.echo.core.uikit.components.EchoImageCarousel
import com.echo.core.utils.files.formatEventDate
import com.echo.features.event.data.models.FullEventDto
import com.echo.features.event.presentation.states.EventDetailsUiState
import com.echo.features.event.presentation.viewModels.EventDetailsViewModel
import kotlin.collections.isNullOrEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    eventId: Int,
    viewModel: EventDetailsViewModel,
    onDismiss: () -> Unit
) {
    LaunchedEffect(eventId) {
        viewModel.loadEventDetails(eventId)
    }

    val state by viewModel.uiState.collectAsState()

    EchoBottomSheet(
        onDismiss = onDismiss
    ) {
        when (val currentState = state) {
            is EventDetailsUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is EventDetailsUiState.Content -> {
                EventDetailsContent(
                    event = currentState.event,
                    onLikeClick = { viewModel.likeEvent(eventId) },
                    onDislikeClick = { viewModel.dislikeEvent(eventId) },
                    onRemoveReaction = { viewModel.removeReaction(eventId) }
                )
            }

            is EventDetailsUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth().height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentState.message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun EventDetailsContent(
    event: FullEventDto,
    onLikeClick: () -> Unit,
    onDislikeClick: () -> Unit,
    onRemoveReaction: () -> Unit
) {
    val images = event.images.orEmpty()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {

        EchoImageCarousel(
            images = event.images.orEmpty().map { it.imageUrl },
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Text(
                text = event.event.title.orEmpty(),
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            ReactionButtons(
                isLiked = event.isLiked,
                isDisliked = event.isDisliked,
                onLikeClick = onLikeClick,
                onDislikeClick = onDislikeClick,
                onRemoveReaction = onRemoveReaction
            )

            Spacer(modifier = Modifier.height(10.dp))

            EchoDivider()

            Spacer(modifier = Modifier.height(10.dp))

            if (!event.event.price.isNullOrBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Rounded.CurrencyRuble,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = event.event.price,
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            val dates = event.event.dates.orEmpty()
            if (dates.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Rounded.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        dates.forEach { date ->
                            Text(
                                text = formatEventDate(date.start, date.end),
                                style = MaterialTheme.typography.displaySmall
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (event.place != null && event.place.title.isNotBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Rounded.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = event.place.title,
                            style = MaterialTheme.typography.displaySmall
                        )
                        if (!event.place.address.isNullOrBlank()) {
                            Text(
                                text = event.place.address,
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (!event.event.bodyText.isNullOrBlank()) {
                EchoDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = event.event.bodyText,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(5.dp))

            EchoDivider()

            Spacer(modifier = Modifier.height(35.dp))
        }

        if (!event.likedByFriends.isNullOrEmpty()) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Rounded.People,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Нравится друзьям",
                        style = MaterialTheme.typography.displaySmall
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        event.likedByFriends.forEach { friend ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val avatarUrl = friend.avatarPath?.let { NetworkModule.UPLOADS_URL + it }
                                    if (avatarUrl != null) {
                                        AsyncImage(model = avatarUrl, contentDescription = null,
                                            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                    } else {
                                        Icon(Icons.Rounded.Person, null, Modifier.size(18.dp),
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                    }
                                }
                                Text(friend.nickname, style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }    }
}

@Composable
private fun ReactionButtons(
    isLiked: Boolean,
    isDisliked: Boolean,
    onLikeClick: () -> Unit,
    onDislikeClick: () -> Unit,
    onRemoveReaction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = if (isLiked) onRemoveReaction else onLikeClick,
            modifier = Modifier.size(44.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = if (isLiked) Color(0xFF4CAF50) else Color.White,
                contentColor = if (isLiked) Color.White else Color(0xFF4CAF50)
            )
        ) {
            Icon(
                imageVector = if (isLiked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                contentDescription = if (isLiked) "Убрать лайк" else "Лайк"
            )
        }

        IconButton(
            onClick = if (isDisliked) onRemoveReaction else onDislikeClick,
            modifier = Modifier.size(44.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = if (isDisliked) Color(0xFFF44336) else Color.White,
                contentColor = if (isDisliked) Color.White else Color(0xFFF44336)
            )
        ) {
            Icon(
                imageVector = if (isDisliked) Icons.Rounded.ThumbDown else Icons.Rounded.ThumbDownOffAlt,
                contentDescription = if (isDisliked) "Убрать дизлайк" else "Дизлайк"
            )
        }
    }
}