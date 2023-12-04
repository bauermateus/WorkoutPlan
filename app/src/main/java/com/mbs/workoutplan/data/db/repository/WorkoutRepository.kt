package com.mbs.workoutplan.data.db.repository

import android.net.Uri
import com.mbs.workoutplan.data.db.models.ExerciseDTO
import com.mbs.workoutplan.data.db.models.WorkoutDTO

interface WorkoutRepository {
    suspend fun getWorkouts(): List<WorkoutDTO>
    suspend fun getWorkoutInfo(documentPath: String): WorkoutDTO?
    suspend fun createNewWorkout(workout: WorkoutDTO): String
    suspend fun createNewExercise(workoutId: String, exercise: ExerciseDTO)
    suspend fun deleteWorkout(workoutId: String)
    suspend fun uploadExercisePhoto(imgPath: Uri): String?
    suspend fun deleteExercise(workoutId: String, exercise: ExerciseDTO)
}