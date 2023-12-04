package com.mbs.workoutplan.presentation.event

sealed interface NewExerciseEvent {
    data class Error(val msg: String) : NewExerciseEvent
    data object CreatedSuccess : NewExerciseEvent
}