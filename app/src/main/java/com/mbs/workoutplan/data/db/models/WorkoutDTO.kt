package com.mbs.workoutplan.data.db.models

import com.google.firebase.Timestamp

data class WorkoutDTO(
    var identifier: String? = null,
    val name: String? = null,
    val description: String? = null,
    val date: Timestamp? = Timestamp.now(),
    val exercises: List<ExerciseDTO>? = null
)
