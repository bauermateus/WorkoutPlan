package com.mbs.workoutplan.data.db.repository

import com.mbs.workoutplan.data.db.models.UserInfoResponse

interface WorkoutRepository {
    suspend fun getUserInfo(): UserInfoResponse
}