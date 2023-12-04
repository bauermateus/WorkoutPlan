package com.mbs.workoutplan.domain.usecase

import android.net.Uri
import com.mbs.workoutplan.data.db.repository.UserInfoRepository


class UploadProfilePictureUseCase(
    private val userInfoRepository: UserInfoRepository
) {
    suspend operator fun invoke(imgPath: Uri) =
        userInfoRepository.uploadUserImg(imgPath)
}