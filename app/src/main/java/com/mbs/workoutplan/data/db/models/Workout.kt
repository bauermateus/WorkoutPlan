package com.mbs.workoutplan.data.db.models

import com.google.firebase.Timestamp

data class Workout(
    var identifier: String? = null,
    val name: Long? = null,
    val description: String? = null,
    val date: Timestamp? = Timestamp.now(),
    val exercises: List<Exercise>? = null
)
