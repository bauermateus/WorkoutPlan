package com.mbs.workoutplan.domain.usecase

import com.mbs.workoutplan.data.auth.repository.AuthSignInRepository

class SignInWithEmailAndPasswordUseCase(
    private val authSignInRepository: AuthSignInRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        authSignInRepository.signIn(email, password)
}