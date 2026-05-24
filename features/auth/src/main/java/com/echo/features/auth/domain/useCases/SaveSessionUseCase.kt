package com.echo.features.auth.domain.useCases;

import com.echo.features.auth.domain.repositories.AuthRepository;
import javax.inject.Inject

class SaveSessionUseCase @Inject constructor (private val repository: AuthRepository) {

    operator fun invoke(nickname: String, password: String, userId: Long){
        repository.saveCredentials(nickname, password, userId)
    }
}
