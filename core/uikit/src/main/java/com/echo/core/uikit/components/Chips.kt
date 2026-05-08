package com.echo.core.uikit.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class ChipState {
    POSITIVE,
    NEGATIVE,
    NEUTRAL
}

@Composable
fun EchoCategoryChip(
    label: String,
    state: Enum<*>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    positiveIcon: ImageVector? = null,
    negativeIcon: ImageVector? = null,
    neutralIcon: ImageVector? = null
) {
    val (containerColor, contentColor, icon) = when (state) {
        ChipState.POSITIVE -> Triple(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            positiveIcon
        )
        ChipState.NEGATIVE -> Triple(
            MaterialTheme.colorScheme.error,
            MaterialTheme.colorScheme.onError,
            negativeIcon
        )
        ChipState.NEUTRAL -> Triple(
            MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
            MaterialTheme.colorScheme.outline,
            neutralIcon
        )
        else -> Triple(
            MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
            MaterialTheme.colorScheme.outline,
            neutralIcon
        )
    }

    AssistChip(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = containerColor,
            labelColor = contentColor,
            leadingIconContentColor = contentColor
        ),
        leadingIcon = icon?.let {
            { Icon(imageVector = it, contentDescription = null, modifier = Modifier.size(18.dp)) }
        },
        label = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = label, style = MaterialTheme.typography.displayMedium)
            }
        }
    )
}