package com.echo

import androidx.lifecycle.ViewModel
import com.echo.core.network.storage.AuthStorage
import jakarta.inject.Inject

class MainViewModel @Inject constructor(
    private val authStorage: AuthStorage
) : ViewModel() {

    sealed class StartDestination {
        object Auth : StartDestination()
        object Feed : StartDestination()
    }

    fun getStartDestination(): StartDestination {
        val creds = authStorage.getCredentials()
        return if (creds != null) StartDestination.Feed else StartDestination.Auth
    }
}
