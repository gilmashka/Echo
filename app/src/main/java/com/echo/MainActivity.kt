package com.echo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import com.echo.core.uikit.ui.theme.EchoTheme
import androidx.compose.ui.tooling.preview.Preview
import com.echo.core.uikit.ui.theme.EchoTheme
import com.echo.core.uikit.components.EchoPrimaryButton
import com.echo.features.auth.presentation.screens.RegisterScreen
import com.echo.features.auth.di.DaggerAuthComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm = DaggerAuthComponent.create().getViewModel()

            RegisterScreen(onSuccess = { userId ->
                println("Ура! Мы зарегистрировались, наш ID: $userId")
            },
                vm)
        }
    }
}

