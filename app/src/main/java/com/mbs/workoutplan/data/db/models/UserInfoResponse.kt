package com.mbs.workoutplan.data.db.models

data class UserInfoResponse(
    val data: UserData? = null,
    val error: Exception? = null
)
