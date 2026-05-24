package com.echo.features.profile.presentation.states

import com.echo.features.profile.data.models.UserDto

sealed class ProfileUiState{

    object Loading: ProfileUiState()

    data class Content(
        val profile: UserDto
    ) : ProfileUiState()

    data class Error(
        val message: String
    ) : ProfileUiState()
}