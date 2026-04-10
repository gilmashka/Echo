package com.echo.features.auth.presentation.states

import android.os.Message

sealed class AuthUiState() {

    object Idle: AuthUiState()

    object Loading: AuthUiState()

    data class Success(val userId: Long) : AuthUiState()

    data class Error(val message: String) : AuthUiState()
}