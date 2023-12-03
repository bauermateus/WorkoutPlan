package com.mbs.workoutplan.presentation.uistate

import com.mbs.workoutplan.data.db.models.Workout
import com.mbs.workoutplan.presentation.event.HomeEvent

data class HomeUiState(
    val workouts: List<Workout>? = null,
    val event: HomeEvent? = null
)
