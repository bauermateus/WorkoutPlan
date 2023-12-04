package com.mbs.workoutplan.presentation.event

sealed interface WorkoutDetailsEvent {
    data class Error(val msg: String) : WorkoutDetailsEvent
    data class DeleteSuccess(val msg: String) : WorkoutDetailsEvent
}