package com.echo.features.auth.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.echo.core.network.models.City
import com.echo.core.network.models.UserForm
import com.echo.core.uikit.components.CityPickerDialog
import com.echo.core.uikit.components.EchoDivider
import com.echo.core.uikit.components.EchoPrimaryButton
import com.echo.core.uikit.components.EchoSecondaryButton
import com.echo.core.uikit.components.EchoTextField
import com.echo.features.auth.presentation.states.AuthUiState
import com.echo.features.auth.presentation.viewModels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onSuccess: (Long) -> Unit,
    onBack: () -> Unit,
    viewModel: AuthViewModel,
) {
    val state by viewModel.uiState.collectAsState()

    var nickname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf(City.KZN) }
    var showCityPicker by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (state is AuthUiState.Success) {
            onSuccess((state as AuthUiState.Success).userId)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            Text(
                text = "Регистрация",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(25.dp))
            EchoDivider()
            Spacer(Modifier.height(25.dp))

            if (state is AuthUiState.Error) {
                Text(
                    text = (state as AuthUiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))
            }

            Spacer(Modifier.height(10.dp))

            EchoTextField(
                value = nickname,
                onValueChange = { nickname = it },
                placeholder = "Nickname",
                modifier = Modifier.width(350.dp),
                leadingIcon = Icons.Rounded.AlternateEmail
            )
            Spacer(Modifier.height(25.dp))

            EchoTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Пароль",
                modifier = Modifier.width(350.dp),
                leadingIcon = Icons.Rounded.Password,
                isPassword = true
            )
            Spacer(Modifier.height(25.dp))

            EchoTextField(
                value = firstName,
                onValueChange = { firstName = it },
                placeholder = "Имя (Опционально)",
                modifier = Modifier.width(350.dp)
            )
            Spacer(Modifier.height(25.dp))

            EchoTextField(
                value = lastName,
                onValueChange = { lastName = it },
                placeholder = "Фамилия (Опционально)",
                modifier = Modifier.width(350.dp)
            )
            Spacer(Modifier.height(25.dp))

            Box(modifier = Modifier.width(350.dp)) {
                EchoTextField(
                    value = selectedCity.displayName,
                    onValueChange = {},
                    placeholder = "Город",
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

            Spacer(Modifier.height(25.dp))

            EchoPrimaryButton(
                text = "Регистрация",
                onClick = {
                    val form = UserForm(nickname.trim(), password.trim(), firstName.trim(), lastName.trim(), city = selectedCity)
                    viewModel.register(form)
                },
                modifier = Modifier.height(55.dp).width(205.dp),
                enabled = state !is AuthUiState.Loading && nickname.isNotBlank() && password.isNotBlank(),
                icon = Icons.Rounded.PersonAdd
            )

            Spacer(Modifier.height(25.dp))

            EchoSecondaryButton(
                text = "Назад",
                onClick = onBack,
                modifier = Modifier.height(55.dp).width(205.dp),
                enabled = state !is AuthUiState.Loading,
                icon = Icons.Rounded.ArrowBack
            )
        }
    }
}