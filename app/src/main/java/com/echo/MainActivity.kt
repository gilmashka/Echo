package com.echo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.echo.core.uikit.ui.theme.EchoTheme
import com.echo.features.auth.di.DaggerAuthComponent
import com.echo.features.auth.presentation.navigation.AuthScreen
import com.echo.features.auth.presentation.screens.LoginScreen
import com.echo.features.auth.presentation.screens.RegisterScreen
import com.echo.features.auth.presentation.screens.StartScreen
import com.echo.features.feed.presentation.screens.FeedScreen
import com.echo.features.feed.di.DaggerFeedComponent


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = (application as EchoApplication).appComponent
        val authComponent = DaggerAuthComponent.builder().appComponent(appComponent).build()
        val feedComponent = DaggerFeedComponent.builder().appComponent(appComponent).build()

        val isLoggedIn = appComponent.authStorage().getCredentials() != null

        setContent {
            EchoTheme {
                var currentScreen by remember {
                    mutableStateOf(if (isLoggedIn) "feed" else AuthScreen.Start.route)
                }

                when (currentScreen) {
                    AuthScreen.Start.route -> {
                        StartScreen(
                            onNavigateToLogin = { currentScreen = AuthScreen.Login.route },
                            onNavigateToRegister = { currentScreen = AuthScreen.Register.route }
                        )
                    }

                    AuthScreen.Login.route -> {
                        LoginScreen(
                            viewModel = daggerViewModel { authComponent.getViewModel() },
                            onSuccess = { currentScreen = "feed" },
                            onBack = { currentScreen = AuthScreen.Start.route }
                        )
                    }


                    AuthScreen.Register.route -> {
                        RegisterScreen(
                            viewModel = daggerViewModel { authComponent.getViewModel() },
                            onSuccess = { currentScreen = "feed" },
                            onBack = { currentScreen = AuthScreen.Start.route }
                        )
                    }


                    "feed" -> {
                        FeedScreen(
                            viewModel = daggerViewModel { feedComponent.getViewModel() }
                        )
                    }
                }
            }
        }
    }
}
