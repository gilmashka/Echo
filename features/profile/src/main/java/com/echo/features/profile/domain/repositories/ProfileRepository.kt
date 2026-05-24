package com.echo.features.profile.domain.repositories

import android.content.Context
import android.net.Uri
import com.echo.core.network.models.UserForm
import com.echo.features.profile.data.models.UserDto
import okhttp3.MultipartBody

interface ProfileRepository {

    suspend fun getUserProfile(): Result<UserDto>
    suspend fun changeUserProfile(form: UserForm): Result<Unit>
    suspend fun deleteUserProfile(): Result<Unit>
    suspend fun deleteUserAvatar(): Result<Unit>
    suspend fun setUserAvatar(uri: Uri, context: Context): Result<Unit>
}