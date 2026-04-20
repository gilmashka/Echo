package com.echo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import com.echo.core.uikit.ui.theme.EchoTheme
import com.echo.features.auth.di.DaggerAuthComponent
import com.echo.features.auth.presentation.screens.RegisterScreen


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = (application as EchoApplication).appComponent

        val authComponent = DaggerAuthComponent.builder()
            .appComponent(appComponent)
            .build()

        val viewModel = authComponent.getViewModel()

        enableEdgeToEdge()
        setContent {

            EchoTheme {
                RegisterScreen(
                    viewModel = viewModel,
                    onSuccess = { userId ->
                        println("Ура! ID: $userId")
                    }
                )
            }
        }
    }
}

