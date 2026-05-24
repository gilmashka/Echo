package com.echo.features.profile.domain.useCases

import com.echo.core.network.models.UserForm
import com.echo.features.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(form: UserForm): Result<Unit> {
        return repository.changeUserProfile(form)
    }
}