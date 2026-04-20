package com.echo.features.auth.data.repositoryImpls

import com.echo.core.network.EchoApi
import com.echo.core.network.models.ServiceLocator
import com.echo.core.network.models.UserForm
import com.echo.core.network.storage.AuthStorage
import com.echo.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: EchoApi,
    private val authStorage: AuthStorage)
    : AuthRepository {


    override suspend fun register(form: UserForm): Result<Long>{
        return try {
            val response = api.register(form)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка регистрации: ${response.code()}"))
            }
        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun login(form: UserForm): Result<Long> {
        return try {
            val response = api.login(form)
            if(response.isSuccessful){

                val userId: Long = response.body() ?: throw Exception("ID не получен")

                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Не удалось войти:" +
                        " ${response.code()}"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override fun saveCredentials(nickname: String, password: String, userId: Long) {
        authStorage.saveSession(nickname, password, userId)
    }
}