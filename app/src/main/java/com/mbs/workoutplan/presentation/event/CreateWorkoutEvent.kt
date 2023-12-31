package com.mbs.workoutplan.presentation.event

sealed interface CreateWorkoutEvent {
    data class Error(val msg: String) : CreateWorkoutEvent
    data class Success(val id: String) : CreateWorkoutEvent
}