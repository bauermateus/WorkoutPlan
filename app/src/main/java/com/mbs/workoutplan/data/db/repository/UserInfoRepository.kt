package com.mbs.workoutplan.data.db.repository

import android.net.Uri
import com.mbs.workoutplan.data.db.models.UserDataDTO

interface UserInfoRepository {
    suspend fun getUserInfo(): UserDataDTO?
    suspend fun uploadUserImg(imgPath: Uri): String?
    suspend fun updateUserInfo(userData: UserDataDTO)
}