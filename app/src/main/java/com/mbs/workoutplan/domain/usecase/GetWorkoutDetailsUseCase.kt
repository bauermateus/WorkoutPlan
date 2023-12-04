package com.mbs.workoutplan.domain.usecase

import com.mbs.workoutplan.data.db.repository.WorkoutRepository

class GetWorkoutDetailsUseCase(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke(id: String) = workoutRepository.getWorkoutInfo(id)
}