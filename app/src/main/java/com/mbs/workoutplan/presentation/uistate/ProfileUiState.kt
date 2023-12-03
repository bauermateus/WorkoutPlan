package com.mbs.workoutplan.presentation.uistate

import com.mbs.workoutplan.data.db.models.UserData
import com.mbs.workoutplan.presentation.event.ProfileEvent

data class ProfileUiState(
    val userData: UserData? = null,
    val event: ProfileEvent? = null
)
