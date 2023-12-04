package com.mbs.workoutplan.presentation.uistate

import com.mbs.workoutplan.data.db.models.WorkoutDTO
import com.mbs.workoutplan.presentation.event.HomeEvent

data class HomeUiState(
    val workouts: List<WorkoutDTO>? = null,
    val event: HomeEvent? = null
)
