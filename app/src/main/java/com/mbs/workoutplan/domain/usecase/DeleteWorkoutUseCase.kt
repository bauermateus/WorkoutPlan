package com.mbs.workoutplan.domain.usecase

import com.mbs.workoutplan.data.db.repository.WorkoutRepository


class DeleteWorkoutUseCase(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke(workoutId: String) =
        workoutRepository.deleteWorkout(workoutId)
}