package com.echo.features.auth.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.echo.core.network.models.UserForm
import com.echo.core.uikit.components.EchoDivider
import com.echo.core.uikit.components.EchoPrimaryButton
import com.echo.core.uikit.components.EchoTextField
import com.echo.core.uikit.ui.theme.EchoTheme
import com.echo.features.auth.di.DaggerAuthComponent
import com.echo.features.auth.presentation.states.AuthUiState
import com.echo.features.auth.presentation.viewModels.AuthViewModel

@Composable
fun LoginScreen(
    onSuccess: (Long) -> Unit,
    viewModel: AuthViewModel
){
    val state by viewModel.uiState.collectAsState()

    var nicknameText : String by rememberSaveable() { mutableStateOf("@") }
    var passwordText : String by rememberSaveable() { mutableStateOf("")}

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
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Spacer(modifier = Modifier.height(300.dp))

            Text(
                text = "Вход",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(10.dp))

            EchoDivider()

            Spacer(Modifier.height(25.dp))

            EchoTextField(
                value = nicknameText,
                onValueChange = {nicknameText = it},
                placeholder = "Nickname (@...)",
                modifier = Modifier.width(350.dp),
                leadingIcon = Icons.Rounded.AlternateEmail
            )

            Spacer(Modifier.height(25.dp))

            EchoTextField(
                value = passwordText,
                onValueChange = {passwordText = it},
                placeholder = "Пароль",
                modifier = Modifier.width(350.dp),
                leadingIcon = Icons.Rounded.Password,
                isPassword = true
            )

            Spacer(Modifier.height(25.dp))

            EchoPrimaryButton(
                text = "Вход",
                onClick = {
                    val form = UserForm(nicknameText, passwordText, firstName = "", lastName = "", city = "")
                    viewModel.login(form)
                },
                modifier = Modifier.height(55.dp).width(205.dp),
                enabled = state !is AuthUiState.Loading && nicknameText.isNotBlank() && passwordText.isNotBlank(),
                icon = Icons.Rounded.PersonAdd
            )

        }
    }
}

