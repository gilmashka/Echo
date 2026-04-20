package com.echo.features.auth.domain.useCases

import com.echo.core.network.models.UserForm
import com.echo.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor (private val repository: AuthRepository) {

    suspend operator fun invoke(form: UserForm): Result<Long> {

        if(form.nickname.trim().isNotEmpty() && form.password.trim().isNotEmpty()){
            return repository.register(form)
        } else {
            return Result.failure(Exception("Поля не могут быть пустыми"))
        }
    }
}