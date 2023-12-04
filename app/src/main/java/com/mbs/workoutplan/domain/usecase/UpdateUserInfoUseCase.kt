package com.mbs.workoutplan.domain.usecase

import com.mbs.workoutplan.data.db.models.UserDataDTO
import com.mbs.workoutplan.data.db.repository.UserInfoRepository


class UpdateUserInfoUseCase(
    private val userInfoRepository: UserInfoRepository
) {
    suspend operator fun invoke(userData: UserDataDTO) =
        userInfoRepository.updateUserInfo(userData)
}