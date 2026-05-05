package com.echo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.echo.core.uikit.ui.theme.EchoTheme
import com.echo.core.uikit.utils.daggerViewModel
import com.echo.features.auth.di.DaggerAuthComponent
import com.echo.features.auth.presentation.navigation.AuthNavHost
import com.echo.features.feed.presentation.screens.FeedScreen
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
                val isLoggedIn = remember {
                    mutableStateOf(appComponent.authStorage().getCredentials() != null)

                }

                if (!isLoggedIn.value) {
                    val authComponent = remember {
                        DaggerAuthComponent.builder()
                            .appComponent(appComponent)
                            .build()
                    }

                    AuthNavHost(
                        authComponent = authComponent,
                        onLoginSuccess = { isLoggedIn.value = true },
                        onRegisterSuccess = { isLoggedIn.value = true }
                    )
                } else {
                    MainScreen(
                        appComponent = appComponent
                    )
                }
            }
        }
    }
}
