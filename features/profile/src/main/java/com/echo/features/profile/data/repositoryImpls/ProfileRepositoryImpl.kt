package com.echo.features.profile.data.repositoryImpls

import android.content.Context
import android.net.Uri
import com.echo.core.network.models.UserForm
import com.echo.core.utils.files.toMultipartBody
import com.echo.features.profile.api.ProfileApi
import com.echo.features.profile.data.models.UserDto
import com.echo.features.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi
): ProfileRepository {

    override suspend fun getUserProfile(): Result<UserDto> {
        return try {
            val response = api.getUserProfile()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка загрузки профиля: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changeUserProfile(form: UserForm): Result<Unit> {
        return try {
            val response = api.changeUserProfile(form)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Ошибка при изменении профиля ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUserProfile(): Result<Unit> {
        return try {
            val response = api.deleteUserProfile()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Ошибка при удалении профиля ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun setUserAvatar(uri: Uri, context: Context): Result<Unit> {
        return try {
            val part = uri.toMultipartBody(context = context, partName = "avatar")
            val response = api.setUserAvatar(part)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Ошибка загрузки аватара: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUserAvatar(): Result<Unit> {
        return try {
            val response = api.deleteUserAvatar()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Ошибка удаления аватара: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}