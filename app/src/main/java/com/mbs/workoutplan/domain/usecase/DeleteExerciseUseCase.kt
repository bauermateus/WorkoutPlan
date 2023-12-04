package com.mbs.workoutplan.domain.usecase

import com.mbs.workoutplan.data.db.models.ExerciseDTO
import com.mbs.workoutplan.data.db.repository.WorkoutRepository


class DeleteExerciseUseCase(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke(workoutId: String, exercise: ExerciseDTO) =
        workoutRepository.deleteExercise(workoutId, exercise)
}