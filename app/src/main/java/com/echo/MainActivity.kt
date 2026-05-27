package com.echo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.echo.core.uikit.ui.theme.EchoTheme
import com.echo.features.auth.di.DaggerAuthComponent
import com.echo.features.auth.presentation.navigation.AuthNavHost
import com.echo.features.main.presentation.navigation.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = (application as EchoApplication).appComponent

        setContent {
            EchoTheme {
                var isLoggedIn by remember {
                    mutableStateOf(appComponent.authStorage().getCredentials() != null)
                }
                var authKey by remember { mutableStateOf(0L) }

                if (!isLoggedIn) {
                    val authComponent = remember {
                        DaggerAuthComponent.builder()
                            .appComponent(appComponent)
                            .build()
                    }

                    AuthNavHost(
                        authComponent = authComponent,
                        onLoginSuccess = {
                            authKey++
                            isLoggedIn = true
                        },
                        onRegisterSuccess = {
                            authKey++
                            isLoggedIn = true
                        }
                    )
                } else {
                    key(authKey) {
                        MainScreen(
                            appComponent = appComponent,
                            onLogout = {
                                appComponent.authStorage().clear()
                                isLoggedIn = false
                            },
                            authKey = authKey
                        )
                    }
                }
            }
        }
    }
}