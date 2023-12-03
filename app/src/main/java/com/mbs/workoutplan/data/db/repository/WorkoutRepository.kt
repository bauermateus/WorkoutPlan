package com.mbs.workoutplan.data.db.repository

import com.mbs.workoutplan.data.db.models.Workout

interface WorkoutRepository {
    suspend fun getWorkouts(): List<Workout>
    suspend fun createNewWorkout(workout: Workout)
    suspend fun editWorkout(workout: Workout)
}