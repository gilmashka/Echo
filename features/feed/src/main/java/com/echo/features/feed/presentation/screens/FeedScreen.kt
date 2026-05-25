package com.echo.features.feed.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SearchOff
import androidx.compose.material.icons.rounded.ThumbDown
import androidx.compose.material.icons.rounded.ThumbDownOffAlt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.echo.core.uikit.components.EchoDivider
import com.echo.core.uikit.components.EchoEventCard
import com.echo.core.uikit.components.EchoPrimaryButton
import com.echo.features.feed.domain.models.FullFeed
import com.echo.features.feed.presentation.states.FeedUiState
import com.echo.features.feed.presentation.viewModels.FeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    onEventClick: (Int) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val filterMode by viewModel.filterMode.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Главная лента v2.0",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(15.dp))
            EchoDivider()
            Spacer(Modifier.height(15.dp))
            FeedFilterBar(
                currentFilter = filterMode,
                onFilterChange = { viewModel.setFilterMode(it) }
            )
            Spacer(Modifier.height(10.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                when (val currentState = state) {
                    is FeedUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is FeedUiState.Content -> {
                        FeedEventList(
                            feed = currentState.feed,
                            filterMode  = filterMode,
                            isRefreshing = currentState.isRefreshing,
                            onRefresh = { viewModel.refreshFeed() },
                            pullRefreshState = pullRefreshState,
                            onEventClick = onEventClick
                        )
                    }

                    is FeedUiState.Error -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = currentState.message,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            EchoPrimaryButton(
                                text = "Повторить",
                                onClick = { viewModel.loadFeed() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedEventList(
    feed: FullFeed,
    filterMode: FeedViewModel.FeedFilterMode,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    pullRefreshState: PullToRefreshState,
    onEventClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = pullRefreshState,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (filterMode) {
                FeedViewModel.FeedFilterMode.ALL -> {
                    item {
                        Text(
                            text = "Любимые категории",
                            style = MaterialTheme.typography.displayMedium)
                    }

                    if (feed.showPlaceholder) {
                        item {
                            Text(
                                text = "Вы не выбрали любимые категории",
                                style = MaterialTheme.typography.displaySmall
                            )
                        }
                    } else {
                        items(feed.favouriteFeed) { event ->
                            EchoEventCard(
                                title = event.title,
                                price = event.price,
                                imageUrl = event.imageUrl,
                                onClick = { onEventClick(event.id) }
                            )
                        }
                    }

                    if (feed.neutralFeed.isNotEmpty()) {
                        item {
                            Spacer(Modifier.height(5.dp))
                            EchoDivider()
                            Spacer(Modifier.height(5.dp))
                            Text("Все события", style = MaterialTheme.typography.displayMedium)
                        }

                        items(feed.neutralFeed) { event ->
                            EchoEventCard(
                                title = event.title,
                                price = event.price,
                                imageUrl = event.imageUrl,
                                onClick = { onEventClick(event.id) }
                            )
                        }
                    }
                }

                FeedViewModel.FeedFilterMode.LIKED -> {
                    item {
                        Text("Нравится", style = MaterialTheme.typography.displayMedium)
                    }

                    if (feed.favouriteFeed.isEmpty()) {
                        item {
                            Text(
                                "Нет понравившихся событий",
                                style = MaterialTheme.typography.displaySmall
                            )
                        }
                    } else {
                        items(feed.favouriteFeed) { event ->
                            EchoEventCard(
                                title = event.title,
                                price = event.price,
                                imageUrl = event.imageUrl,
                                onClick = { onEventClick(event.id) }
                            )
                        }
                    }
                }

                FeedViewModel.FeedFilterMode.DISLIKED -> {
                    item {
                        Text("Не нравится", style = MaterialTheme.typography.displayMedium)
                    }

                    if (feed.favouriteFeed.isEmpty()) {
                        item {
                            Text(
                                "Нет скрытых событий",
                                style = MaterialTheme.typography.displaySmall
                            )
                        }
                    } else {
                        items(feed.favouriteFeed) { event ->
                            EchoEventCard(
                                title = event.title,
                                price = event.price,
                                imageUrl = event.imageUrl,
                                onClick = { onEventClick(event.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedFilterBar(
    currentFilter: FeedViewModel.FeedFilterMode,
    onFilterChange: (FeedViewModel.FeedFilterMode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        FilterChip(
            modifier = Modifier.padding(horizontal = 4.dp),
            selected = currentFilter == FeedViewModel.FeedFilterMode.ALL,
            onClick = { onFilterChange(FeedViewModel.FeedFilterMode.ALL) },
            label = { Text(
                text = "Все",
                style = MaterialTheme.typography.displaySmall
            ) },
            leadingIcon = {
                Icon(
                    if (currentFilter == FeedViewModel.FeedFilterMode.ALL) Icons.Rounded.Search
                    else Icons.Rounded.SearchOff,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        )

        Spacer(Modifier.width(8.dp))

        FilterChip(
            modifier = Modifier.padding(horizontal = 4.dp),
            selected = currentFilter == FeedViewModel.FeedFilterMode.LIKED,
            onClick = { onFilterChange(FeedViewModel.FeedFilterMode.LIKED) },
            label = { Text(
                text ="Нравится",
                style = MaterialTheme.typography.displaySmall
            ) },
            leadingIcon = {
                Icon(
                    if (currentFilter == FeedViewModel.FeedFilterMode.LIKED) Icons.Rounded.Favorite
                    else Icons.Rounded.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        )

        Spacer(Modifier.width(8.dp))

        FilterChip(
            modifier = Modifier.padding(horizontal = 4.dp),
            selected = currentFilter == FeedViewModel.FeedFilterMode.DISLIKED,
            onClick = { onFilterChange(FeedViewModel.FeedFilterMode.DISLIKED) },
            label = { Text(
                text = "Скрытое",
                style = MaterialTheme.typography.displaySmall
            ) },
            leadingIcon = {
                Icon(
                    if (currentFilter == FeedViewModel.FeedFilterMode.DISLIKED) Icons.Rounded.ThumbDown
                    else Icons.Rounded.ThumbDownOffAlt,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        )
    }
}