package com.echo.core.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.echo.core.network.di.NetworkModule

@Composable
fun EchoAvatarBox(
    avatarPath: String?,
    size: Dp = 200.dp,
    borderWidth: Dp = 4.dp,
    modifier: Modifier = Modifier
) {
    val avatarUrl = avatarPath?.let { NetworkModule.UPLOADS_URL + it }

    Box(
        modifier = modifier
            .size(size)
            .border(borderWidth, MaterialTheme.colorScheme.primary, CircleShape)
            .border(borderWidth + 2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        if (avatarUrl != null) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = "Аватар",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                Icons.Rounded.Person, null, Modifier.size(size * 0.45f),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}