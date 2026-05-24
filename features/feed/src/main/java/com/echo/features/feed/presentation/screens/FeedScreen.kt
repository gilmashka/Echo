package com.echo.features.feed.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.echo.core.uikit.components.EchoDivider
import com.echo.core.uikit.components.EchoEventCard
import com.echo.core.uikit.components.EchoPrimaryButton
import com.echo.features.feed.presentation.states.FeedUiState
import com.echo.features.feed.presentation.viewModels.FeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    onEventClick: (Int) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
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
                text = "Главная лента v1.0",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(25.dp))
            EchoDivider()
            Spacer(Modifier.height(25.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                when (val currentState = state) {
                    is FeedUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is FeedUiState.Content -> {
                        val feed = currentState.feed

                        PullToRefreshBox(
                            isRefreshing = currentState.isRefreshing,
                            onRefresh = { viewModel.refreshFeed() },
                            state = pullRefreshState,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                item {
                                    Text("Любимое", style = MaterialTheme.typography.displayMedium)
                                }

                                if (feed.showPlaceholder) {
                                    item { Text(
                                        text = "Вы не выбрали любимые категории",
                                        style = MaterialTheme.typography.displaySmall) }
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