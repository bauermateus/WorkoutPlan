package com.mbs.workoutplan.data.db.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExercisesList(
    val exercises: ArrayList<Exercise>? = null
) : Parcelable