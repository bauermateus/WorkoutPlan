package com.mbs.workoutplan.data.db.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val name: Int? = null,
    val image: String? = null,
    val obs: String? = null
) : Parcelable
