package com.echo.features.profile.domain.useCases

import com.echo.features.profile.data.models.UserDto
import com.echo.features.profile.data.repositoryImpls.ProfileRepositoryImpl
import com.echo.features.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(): Result<UserDto> {
        return repository.getUserProfile()
    }
}