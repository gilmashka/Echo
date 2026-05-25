package com.echo.core.uikit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun EchoImageCarousel(
    images: List<String>,
    modifier: Modifier = Modifier,
    height: Int = 250,
    spacing: Int = 12,
    cornerRadius: Int = 16
) {
    if (images.isEmpty()) return

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(spacing.dp)
    ) {
        items(images) { imageUrl ->
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillParentMaxWidth(0.95f),
                shape = RoundedCornerShape(cornerRadius.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}