package com.mbs.workoutplan.presentation.uistate

import com.mbs.workoutplan.data.db.models.UserDataDTO
import com.mbs.workoutplan.presentation.event.ProfileEvent

data class ProfileUiState(
    val userData: UserDataDTO? = null,
    val event: ProfileEvent? = null
)
