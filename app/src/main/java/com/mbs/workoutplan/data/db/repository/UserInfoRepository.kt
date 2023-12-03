package com.mbs.workoutplan.data.db.repository

import com.mbs.workoutplan.data.db.models.UserData

interface UserInfoRepository {
    suspend fun getUserInfo(): UserData
}