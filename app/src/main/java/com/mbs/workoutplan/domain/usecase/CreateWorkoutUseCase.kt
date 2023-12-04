package com.mbs.workoutplan.domain.usecase

import com.mbs.workoutplan.data.db.models.WorkoutDTO
import com.mbs.workoutplan.data.db.repository.WorkoutRepository

class CreateWorkoutUseCase(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke(workout: WorkoutDTO) =
        workoutRepository.createNewWorkout(workout)
}