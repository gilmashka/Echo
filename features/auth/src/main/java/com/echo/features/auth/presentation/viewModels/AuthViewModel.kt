package com.echo.features.auth.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.echo.core.network.models.UserForm
import com.echo.features.auth.domain.useCases.LoginUseCase
import com.echo.features.auth.domain.useCases.RegisterUseCase
import com.echo.features.auth.domain.useCases.SaveSessionUseCase
import com.echo.features.auth.presentation.states.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val saveSessionUseCase: SaveSessionUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun register(form: UserForm){
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            val result = registerUseCase(form)

            result.onSuccess {
                userId ->
                saveSessionUseCase(
                    nickname = form.nickname,
                    password = form.password,
                    userId = userId)
                _uiState.value = AuthUiState.Success(userId)
            }
            result.onFailure {
                _uiState.value = AuthUiState.Error(it.message ?: "Неизвестная ошибка")
            }
        }
    }

    fun login(form: UserForm){
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            val result = loginUseCase(form)

            result.onSuccess {
                userId ->
                saveSessionUseCase(
                    nickname = form.nickname,
                    password = form.password,
                    userId = userId)
                _uiState.value = AuthUiState.Success(userId)
            }
            result.onFailure {
                _uiState.value = AuthUiState.Error(it.message ?: "Неизвестная ошибка")
            }
        }
    }
}