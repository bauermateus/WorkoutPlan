package com.mbs.workoutplan.data.auth.repository

interface AuthSignInRepository {
    suspend fun signIn(email: String, password: String): Boolean
}