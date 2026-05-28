package com.echo.features.auth.data.repositoryImpls

import android.util.Log
import com.echo.core.network.EchoApi
import com.echo.core.network.models.UserForm
import com.echo.core.network.storage.AuthStorage
import com.echo.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: EchoApi,
    private val authStorage: AuthStorage)
    : AuthRepository {


    override suspend fun register(form: UserForm): Result<Long> {
        return try {
            val response = api.register(form)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Ошибка регистрации"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun saveCredentials(nickname: String, password: String, userId: Long) {
        authStorage.saveSession(nickname.trim(), password.trim(), userId)
    }

    override suspend fun login(form: UserForm): Result<Long> {
        return try {
            authStorage.saveSession(form.nickname, form.password, 0L)
            val response = api.checkAuth()
            if (response.isSuccessful) {
                Result.success(0L)
            } else {
                authStorage.clear()
                Result.failure(Exception("Неверный логин или пароль"))
            }
        } catch (e: Exception) {
            authStorage.clear()
            Result.failure(e)
        }
    }
}