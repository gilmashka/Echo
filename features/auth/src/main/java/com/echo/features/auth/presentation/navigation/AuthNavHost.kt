package com.echo.features.auth.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.echo.core.uikit.utils.daggerViewModel
import com.echo.features.auth.di.AuthComponent
import com.echo.features.auth.presentation.screens.LoginScreen
import com.echo.features.auth.presentation.screens.RegisterScreen
import com.echo.features.auth.presentation.screens.StartScreen


object AuthRoutes{
    const val START = "start"
    const val LOGIN = "login"
    const val REGISTER = "register"
}

@Composable
fun AuthNavHost(
    authComponent: AuthComponent,
    onLoginSuccess: () -> Unit,
    onRegisterSuccess: () -> Unit
){
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AuthRoutes.START
    ){
        composable(AuthRoutes.START){
            StartScreen(
                onNavigateToLogin = {
                    navController.navigate(AuthRoutes.LOGIN)
                },
                onNavigateToRegister = {
                    navController.navigate(AuthRoutes.REGISTER)
                }
            )
        }

        composable(AuthRoutes.LOGIN){
            LoginScreen(
                viewModel = daggerViewModel { authComponent.getViewModel() },
                onSuccess = { onLoginSuccess() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(AuthRoutes.REGISTER) {
            RegisterScreen(
                viewModel = daggerViewModel { authComponent.getViewModel() },
                onSuccess = { onRegisterSuccess() },
                onBack = { navController.popBackStack() }
            )
        }
    }
}