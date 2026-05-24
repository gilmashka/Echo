package com.echo.features.category.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.ThumbDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.echo.core.uikit.components.ChipState
import com.echo.core.uikit.components.EchoCategoryChip
import com.echo.core.uikit.components.EchoDivider
import com.echo.core.uikit.components.EchoPrimaryButton
import com.echo.features.category.data.models.CategoryPreferenceDto
import com.echo.features.category.data.models.PreferenceType
import com.echo.features.category.presentation.states.CategoryPickerUiState
import com.echo.features.category.presentation.viewModels.CategoryPickerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryPickerScreen(
    viewModel: CategoryPickerViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Интересы",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            EchoDivider()

            Spacer(modifier = Modifier.height(12.dp))

            when (val currentState = state) {
                is CategoryPickerUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is CategoryPickerUiState.Content -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(currentState.categories) { category ->
                            CategoryItem(
                                category = category,
                                onClick = { viewModel.onPreferenceClick(category) }
                            )
                        }
                    }
                }

                is CategoryPickerUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = currentState.message,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            EchoPrimaryButton(
                                text = "Повторить",
                                onClick = { viewModel.loadCategories() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(
    category: CategoryPreferenceDto,
    onClick: () -> Unit
) {
    val chipState = when (category.preferenceType) {
        PreferenceType.LIKE -> ChipState.POSITIVE
        PreferenceType.HATE -> ChipState.NEGATIVE
        PreferenceType.NEUTRAL -> ChipState.NEUTRAL
        else -> PreferenceType.NEUTRAL
    }

    EchoCategoryChip(
        label = category.name,
        state = chipState,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        positiveIcon = Icons.Rounded.Favorite,
        negativeIcon = Icons.Rounded.ThumbDown,
        neutralIcon = Icons.Rounded.FavoriteBorder
    )
}