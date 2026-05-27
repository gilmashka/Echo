package com.echo.features.profile.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.echo.features.profile.presentation.viewModels.ProfileViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.echo.core.uikit.components.*


@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val profile = (state as? com.echo.features.profile.presentation.states.ProfileUiState.Content)?.profile ?: return
    val context = LocalContext.current

    var nickname by remember { mutableStateOf(profile.nickname) }
    var firstName by remember { mutableStateOf(profile.firstName.orEmpty()) }
    var lastName by remember { mutableStateOf(profile.lastName.orEmpty()) }
    var password by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf(profile.city) }
    var showCityPicker by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.updateAvatar(it, context) }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                EchoPrimaryIconButton(
                    icon = Icons.Rounded.PhotoCamera,
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier
                        .size(50.dp)
                        .offset(x = 12.dp)
                        .zIndex(1f),
                    contentDescription = "Изменить фото"
                )

                EchoAvatarBox(
                    avatarPath = profile.avatarPath,
                    size = 200.dp
                )

                EchoPrimaryIconButton(
                    icon = Icons.Rounded.Delete,
                    onClick = { viewModel.deleteAvatar() },
                    modifier = Modifier
                        .size(50.dp)
                        .offset(x = (-12).dp)
                        .zIndex(1f),
                    contentDescription = "Удалить фото",
                    isDangerous = true
                )
            }

            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Редактирование профиля", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(16.dp))

                    EchoTextField(
                        value = nickname,
                        onValueChange = { nickname = it },
                        placeholder = "Введите никнейм",
                        label = "Никнейм"
                    )
                    Spacer(Modifier.height(12.dp))
                    EchoTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholder = "Введите имя (Опционально)",
                        label = "Имя"
                    )
                    Spacer(Modifier.height(12.dp))
                    EchoTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholder = "Введите фамилию (Опционально)",
                        label = "Фамилия"
                    )
                    Spacer(Modifier.height(12.dp))

                    Text("Город", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(4.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        EchoTextField(
                            value = selectedCity.displayName,
                            onValueChange = {},
                            placeholder = "Выберите город",
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Box(modifier = Modifier.matchParentSize().clickable { showCityPicker = true })
                    }
                    if (showCityPicker) {
                        CityPickerDialog(
                            currentCity = selectedCity,
                            onCitySelected = { selectedCity = it; showCityPicker = false },
                            onDismiss = { showCityPicker = false }
                        )
                    }

                    Spacer(Modifier.height(12.dp))
                    EchoTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "Новый пароль",
                        label = "Новый пароль (Если меняете)",
                        isPassword = true
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            EchoPrimaryButton(
                text = "Сохранить",
                onClick = { viewModel.updateProfile(nickname, firstName, lastName, selectedCity, password); onBack() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                icon = Icons.Rounded.Check
            )

            Spacer(Modifier.height(12.dp))

            EchoSecondaryButton(
                text = "Назад",
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                icon = Icons.Rounded.ArrowBack
            )

            Spacer(Modifier.height(48.dp))

            EchoDivider()
            Spacer(Modifier.height(24.dp))
            EchoPrimaryButton(
                text = "Удалить профиль",
                onClick = { showDeleteDialog = true },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                isDangerous = true,
                icon = Icons.Rounded.Delete
            )
            Spacer(Modifier.height(32.dp))
        }
    }

    if (showDeleteDialog) {
        EchoConfirmDialog(
            title = "Удаление профиля",
            message = "Вы уверены? Это необратимо.",
            confirmText = "Удалить",
            onConfirm = {
                showDeleteDialog = false
                viewModel.deleteProfile(onLogout)
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
}
