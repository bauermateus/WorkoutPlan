package com.mbs.workoutplan.domain.usecase

import com.mbs.workoutplan.data.db.repository.UserInfoRepository


class GetUserInfoUseCase(
    private val userInfoRepository: UserInfoRepository
) {
    suspend operator fun invoke() =
        userInfoRepository.getUserInfo()
}