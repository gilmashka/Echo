package com.echo.features.profile.domain.useCases

import android.content.Context
import android.net.Uri
import com.echo.features.profile.domain.repositories.ProfileRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class SetAvatarUseCase @Inject constructor(
    private val repository: ProfileRepository
){

    suspend operator fun invoke(uri: Uri, context: Context): Result<Unit> {
        return repository.setUserAvatar(uri = uri, context = context)
    }
}