package com.echo.features.profile.domain.useCases

import com.echo.features.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class DeleteAvatarUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        return repository.deleteUserAvatar()
    }
}