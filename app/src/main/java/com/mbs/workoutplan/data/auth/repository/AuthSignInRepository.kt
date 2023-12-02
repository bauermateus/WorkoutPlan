package com.mbs.workoutplan.data.auth.repository

import com.mbs.workoutplan.data.auth.SignInResult

interface AuthSignInRepository {
    suspend fun signIn(email: String, password: String): SignInResult
}