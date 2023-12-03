package com.mbs.workoutplan.presentation.event

sealed interface HomeEvent {
    data class Error(val msg: String) : HomeEvent
}