package com.echo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import com.echo.features.auth.presentation.screens.RegisterScreen
import com.echo.features.auth.di.DaggerAuthComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegisterScreen(
                onSuccess = { userId ->
                    println("Ура! Мы зарегистрировались, наш ID: $userId")
                }
            )
        }
    }
}

