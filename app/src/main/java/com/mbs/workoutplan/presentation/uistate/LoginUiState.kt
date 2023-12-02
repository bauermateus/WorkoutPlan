package com.mbs.workoutplan.presentation.uistate

import com.mbs.workoutplan.presentation.event.LoginEvent

data class LoginUiState(
    val event: LoginEvent? = null,
    val loading: Boolean = false,
    val isPasswordFilledCorrectly: Boolean = false,
    val isEmailFilledCorrectly: Boolean = false
)

val LoginUiState.isAllFieldsFilledCorrectly get() = isEmailFilledCorrectly && isPasswordFilledCorrectly