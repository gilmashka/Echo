package com.echo.features.auth.domain.repositories

import com.echo.core.network.models.UserForm

interface AuthRepository {

    suspend fun register(form: UserForm): Result<Long>

    suspend fun login(form: UserForm): Result<Long>

    fun saveCredentials(nickname: String, password: String, userId: Long)
}