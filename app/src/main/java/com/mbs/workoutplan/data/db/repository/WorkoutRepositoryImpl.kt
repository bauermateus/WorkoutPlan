package com.mbs.workoutplan.data.db.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.mbs.workoutplan.data.db.models.Workout
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class WorkoutRepositoryImpl : WorkoutRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getWorkouts(): List<Workout> = suspendCoroutine { continuation ->
        db.collection("workouts")
            .document(Firebase.auth.currentUser?.uid ?: return@suspendCoroutine)
            .collection("workouts")
            .get()
            .addOnSuccessListener { query ->
                val workouts = query.map {
                    it.toObject(Workout::class.java).apply { identifier = it.id }
                }
//                val workouts = query.map {
//                    Workout(
//                        identifier = it.id,
//                        name = it.getLong("name"),
//                        date = it.getTimestamp("date"),
//                        description = it.getString("description"),
//                        exercises = it.get("exercises")
//                    )
//                }
                continuation.resume(workouts)
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    override suspend fun createNewWorkout(workout: Workout) = suspendCoroutine { continuation ->
        val workoutHashMap = hashMapOf(
            "name" to workout.name,
            "date" to workout.date,
            "description" to workout.description,
            "exercises" to workout.exercises
        )
        db.collection("workouts")
            .document(Firebase.auth.currentUser?.uid ?: return@suspendCoroutine)
            .collection("workouts")
            .add(workoutHashMap)
            .addOnSuccessListener {
                continuation.resume(Unit)
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    override suspend fun editWorkout(workout: Workout) = suspendCoroutine { continuation ->
        val workoutHashMap = hashMapOf(
            "name" to workout.name,
            "date" to workout.date,
            "description" to workout.description,
            "exercises" to workout.exercises
        )
        db.collection("workouts")
            .document(Firebase.auth.currentUser?.uid ?: return@suspendCoroutine)
            .collection("workouts")
            .whereEqualTo("", "")
            .get()
            .addOnSuccessListener {
                continuation.resume(Unit)
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }
}