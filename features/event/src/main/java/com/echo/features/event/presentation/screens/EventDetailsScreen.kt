package com.echo.features.event.presentation.screens

import android.credentials.CredentialOption
import androidx.annotation.experimental.Experimental
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.echo.core.uikit.components.EchoBottomSheet
import com.echo.core.uikit.components.EchoDivider
import com.echo.features.event.data.models.EventDateDto
import com.echo.features.event.data.models.FullEventDto
import com.echo.features.event.presentation.states.EventDetailsUiState
import com.echo.features.event.presentation.viewModels.EventDetailsViewModel
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY_PROPERTY_NAME
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

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
                EventDetailsContent(event = currentState.event)
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
    event: FullEventDto
) {
    val images = event.images.orEmpty()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {

        if (images.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(images) { image ->
                    AsyncImage(
                        model = image.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillParentMaxWidth(0.85f),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Text(
                text = event.event.title.orEmpty(),
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            EchoDivider()

            Spacer(modifier = Modifier.height(10.dp))

            if (!event.event.price.isNullOrBlank()) {
                Text(
                    text = event.event.price,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
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
                                text = formatDate(date),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (event.place != null && !event.place.title.isNullOrBlank()) {
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
                            style = MaterialTheme.typography.bodyLarge
                        )
                        if (!event.place.address.isNullOrBlank()) {
                            Text(
                                text = event.place.address,
                                style = MaterialTheme.typography.bodyMedium,
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
    }
}

private fun formatDate(date: EventDateDto): String {
    val formatter = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("ru"))
    val startDate = Date(date.start * 1000)
    val endDate = Date(date.end * 1000)
    return "${formatter.format(startDate)} - ${formatter.format(endDate)}"
}