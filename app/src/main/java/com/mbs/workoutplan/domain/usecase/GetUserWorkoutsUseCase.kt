package com.mbs.workoutplan.domain.usecase

import com.mbs.workoutplan.data.db.repository.WorkoutRepository


class GetUserWorkoutsUseCase(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke() =
        workoutRepository.getWorkouts()
}