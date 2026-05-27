package com.echo.features.profile.presentation.viewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.echo.core.network.models.City
import com.echo.core.network.models.UserForm
import com.echo.core.network.storage.AuthStorage
import com.echo.features.profile.domain.useCases.DeleteAvatarUseCase
import com.echo.features.profile.domain.useCases.DeleteProfileUseCase
import com.echo.features.profile.domain.useCases.GetProfileUseCase
import com.echo.features.profile.domain.useCases.SetAvatarUseCase
import com.echo.features.profile.domain.useCases.UpdateProfileUseCase
import com.echo.features.profile.presentation.states.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val setAvatarUseCase: SetAvatarUseCase,
    private val deleteAvatarUseCase: DeleteAvatarUseCase,
    private val authStorage: AuthStorage
): ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

//    init {
//        loadProfile()
//    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            getProfileUseCase()
                .onSuccess { user ->
                    _uiState.value = ProfileUiState.Content(user)
                }
                .onFailure { error ->
                    _uiState.value = ProfileUiState.Error(
                        error.message ?: "Ошибка загрузки"
                    )
                }
        }
    }

    fun updateProfile(
        nickname: String,
        firstName: String,
        lastName: String,
        city: City,
        password: String
    ) {
        viewModelScope.launch {
            val form = UserForm(
                nickname = nickname,
                password = password,
                firstName = firstName,
                lastName = lastName,
                city = city
            )
            updateProfileUseCase(form)
                .onSuccess {
                    Log.d("ProfileVM", "update success, reloading profile")
                    authStorage.saveSession(
                        nickname = nickname,
                        password = password.ifEmpty { authStorage.getCredentials()?.second.orEmpty() },
                        userId = 0L
                    )
                    loadProfile()
                }
                .onFailure { error ->
                    _uiState.value = ProfileUiState.Error(
                        error.message ?: "Ошибка сохранения"
                    )
                }
        }
    }

    fun updateAvatar(uri: Uri, context: Context) {
        viewModelScope.launch {
            setAvatarUseCase(uri, context)
                .onSuccess { loadProfile() }
                .onFailure {
                    error -> _uiState.value = ProfileUiState.Error(
                        error.message ?: "Ошибка загрузки аватара"
                    )
                }
        }
    }

    fun deleteAvatar() {
        viewModelScope.launch {
            deleteAvatarUseCase()
                .onSuccess { loadProfile() }
                .onFailure {
                    error -> _uiState.value = ProfileUiState.Error(
                        error.message ?: "Ошибка удаления аватара"
                    )
                }
        }
    }

    fun deleteProfile(onSuccess: () -> Unit) {
        viewModelScope.launch {
            deleteProfileUseCase()
                .onSuccess { onSuccess() }
                .onFailure {
                    error -> _uiState.value = ProfileUiState.Error(
                        error.message ?: "Ошибка удаления профиля"
                    )

                }
        }
    }
}