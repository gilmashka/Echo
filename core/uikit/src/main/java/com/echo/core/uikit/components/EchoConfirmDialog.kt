package com.echo.core.uikit.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EchoConfirmDialog(
    title: String = "Подтверждение",
    message: String,
    confirmText: String = "Да",
    dismissText: String = "Нет",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surface,
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.displayMedium
            ) },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.displaySmall)},
        confirmButton = {
            EchoPrimaryButton(
                text = confirmText,
                onClick = onConfirm,
                isDangerous = true
            )
        },
        dismissButton = {
            EchoSecondaryButton(
                text = dismissText,
                onClick = onDismiss
            )
        }
    )
}