package com.mbs.workoutplan.presentation.event

sealed interface EditProfileEvent {
    data class Error(val msg: String) : EditProfileEvent
    data class Success(val msg: String) : EditProfileEvent
}