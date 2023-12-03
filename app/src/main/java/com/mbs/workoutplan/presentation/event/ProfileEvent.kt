package com.mbs.workoutplan.presentation.event

sealed interface ProfileEvent {
    data class Error(val msg: String) : ProfileEvent
}