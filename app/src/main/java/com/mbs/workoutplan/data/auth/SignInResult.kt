package com.mbs.workoutplan.data.auth

data class SignInResult(
    val success: Boolean = false,
    val error: Exception? = null
)
