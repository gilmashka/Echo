package com.echo.features.profile.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.echo.core.network.di.NetworkModule
import com.echo.core.uikit.components.EchoAvatarBox
import com.echo.core.uikit.components.EchoConfirmDialog
import com.echo.core.uikit.components.EchoDivider
import com.echo.core.uikit.components.EchoPrimaryButton
import com.echo.core.uikit.components.EchoPrimaryIconButton
import com.echo.core.uikit.components.EchoSecondaryIconButton
import com.echo.features.profile.data.models.UserDto
import com.echo.features.profile.presentation.states.ProfileUiState
import com.echo.features.profile.presentation.viewModels.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateToEdit: () -> Unit,
    onLogout: () -> Unit = {},
    onThemeClick: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    val state by viewModel.uiState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (val currentState = state) {
            is ProfileUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ProfileUiState.Content -> {
                ProfileContent(
                    profile = currentState.profile,
                    onEditClick = onNavigateToEdit,
                    onLogoutClick = { showLogoutDialog = true },
                    onThemeClick = onThemeClick
                )
            }
            is ProfileUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = currentState.message, color = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(16.dp))
                        EchoPrimaryButton(text = "Повторить", onClick = { viewModel.loadProfile() })
                    }
                }
            }
        }
    }

    if (showLogoutDialog) {
        EchoConfirmDialog(
            title = "Выход",
            message = "Вы уверены, что хотите выйти?",
            onConfirm = {
                showLogoutDialog = false
                onLogout()
            },
            onDismiss = { showLogoutDialog = false }
        )
    }
}

@Composable
private fun ProfileContent(
    profile: UserDto,
    onEditClick: () -> Unit,
    onThemeClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            EchoAvatarBox(
                avatarPath = profile.avatarPath,
                size = 200.dp,
                modifier = Modifier.align(Alignment.Center)
            )

            ControlButtonsColumn(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
                onEditClick = onEditClick,
                onThemeClick = onThemeClick,
                onLogoutClick = onLogoutClick
            )
        }

        Spacer(Modifier.height(24.dp))

        ProfileInfoCard(profile)
    }
}

@Composable
private fun ProfileInfoCard(profile: UserDto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ProfileInfoRow("Никнейм", profile.nickname)
            EchoDivider(modifier = Modifier.padding(vertical = 12.dp))
            ProfileInfoRow("Имя", profile.firstName.orEmpty())
            EchoDivider(modifier = Modifier.padding(vertical = 12.dp))
            ProfileInfoRow("Фамилия", profile.lastName.orEmpty())
            EchoDivider(modifier = Modifier.padding(vertical = 12.dp))
            ProfileInfoRow("Город", profile.city.displayName)
        }
    }
}
@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = value,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
@Composable
private fun ControlButtonsColumn(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onThemeClick: () -> Unit,
    onLogoutClick: () -> Unit
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EchoPrimaryIconButton(
            icon = Icons.Rounded.Edit,
            onClick = onEditClick,
            modifier = Modifier.size(50.dp),
            contentDescription = "Редактировать"
        )
        EchoSecondaryIconButton(
            icon = Icons.Rounded.Palette,
            onClick = onThemeClick,
            modifier = Modifier.size(50.dp),
            contentDescription = "Сменить тему"
        )
        EchoPrimaryIconButton(
            icon = Icons.Rounded.Logout,
            onClick = onLogoutClick,
            modifier = Modifier.size(50.dp),
            contentDescription = "Выйти",
            isDangerous = true
        )
    }
}