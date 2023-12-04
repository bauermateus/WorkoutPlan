package com.mbs.workoutplan.domain.usecase

import android.net.Uri
import com.mbs.workoutplan.data.db.repository.WorkoutRepository


class UploadPhotoUseCase(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke(imgUri: Uri) =
        workoutRepository.uploadExercisePhoto(imgUri)
}