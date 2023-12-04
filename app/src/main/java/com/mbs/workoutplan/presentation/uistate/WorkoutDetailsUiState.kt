package com.mbs.workoutplan.presentation.uistate

import com.mbs.workoutplan.data.db.models.WorkoutDTO
import com.mbs.workoutplan.presentation.event.WorkoutDetailsEvent

data class WorkoutDetailsUiState(
    val workout: WorkoutDTO? = null,
    val event: WorkoutDetailsEvent? = null
)
