package com.mbs.workoutplan.presentation.event

sealed interface LoginEvent {
    data object Success : LoginEvent
    data class Error(val msg: String) : LoginEvent
}