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
import com.echo.features.auth.presentation.navigation.AuthNavHost
import com.echo.features.feed.di.DaggerFeedComponent
import com.echo.features.main.presentation.navigation.MainScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = (application as EchoApplication).appComponent
        val authComponent = DaggerAuthComponent.builder().appComponent(appComponent).build()
        val feedComponent = DaggerFeedComponent.builder().appComponent(appComponent).build()

        val isLoggedIn = appComponent.authStorage().getCredentials() != null

        setContent {
            EchoTheme {
                var isLoggedIn by remember {
                    mutableStateOf(appComponent.authStorage().getCredentials() != null)
                }

                if (!isLoggedIn) {
                    val authComponent = remember {
                        DaggerAuthComponent.builder()
                            .appComponent(appComponent)
                            .build()
                    }

                    AuthNavHost(
                        authComponent = authComponent,
                        onLoginSuccess = { isLoggedIn = true },
                        onRegisterSuccess = { isLoggedIn = true }
                    )
                } else {
                    MainScreen(
                        appComponent = appComponent,
                        onLogout = {
                            appComponent.authStorage().clear()
                            isLoggedIn = false
                        }
                    )
                }
            }
        }
    }
}
