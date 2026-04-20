package com.echo.features.auth.presentation.navigation
sealed class AuthScreen(val route: String) {
    object Start : AuthScreen("start")
    object Login : AuthScreen("login")
    object Register : AuthScreen("register")
}