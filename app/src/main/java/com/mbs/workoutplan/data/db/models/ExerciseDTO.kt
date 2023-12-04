package com.mbs.workoutplan.data.db.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseDTO(
    val name: String? = null,
    val image: String? = null,
    val obs: String? = null
) : Parcelable
