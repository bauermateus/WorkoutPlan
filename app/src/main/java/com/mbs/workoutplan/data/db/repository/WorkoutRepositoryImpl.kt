package com.mbs.workoutplan.data.db.repository

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mbs.workoutplan.data.db.models.ExerciseDTO
import com.mbs.workoutplan.data.db.models.WorkoutDTO
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class WorkoutRepositoryImpl : WorkoutRepository {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    override suspend fun getWorkouts(): List<WorkoutDTO> = suspendCoroutine { continuation ->
        db.collection("workouts")
            .document(Firebase.auth.currentUser?.uid ?: return@suspendCoroutine)
            .collection("workouts")
            .get()
            .addOnSuccessListener { query ->
                val workouts = query.map {
                    it.toObject(WorkoutDTO::class.java).apply { identifier = it.id }
                }
                continuation.resume(workouts)
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    override suspend fun getWorkoutInfo(documentPath: String) = suspendCoroutine { continuation ->
        db.collection("workouts")
            .document(Firebase.auth.currentUser?.uid ?: return@suspendCoroutine)
            .collection("workouts")
            .document(documentPath)
            .get()
            .addOnSuccessListener {
                continuation.resume(
                    it.toObject(WorkoutDTO::class.java)?.apply { identifier = it.id })
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    override suspend fun createNewWorkout(workout: WorkoutDTO) = suspendCoroutine { continuation ->
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
                continuation.resume(it.id)
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    override suspend fun createNewExercise(workoutId: String, exercise: ExerciseDTO) =
        suspendCoroutine { continuation ->
            db.collection("workouts")
                .document(Firebase.auth.currentUser?.uid ?: return@suspendCoroutine)
                .collection("workouts")
                .document(workoutId)
                .get()
                .addOnSuccessListener { document ->
                    val arrayExercise =
                        document.toObject(WorkoutDTO::class.java)?.exercises?.toMutableList()
                            ?: mutableListOf()
                    arrayExercise.add(exercise)
                    document.reference.update("exercises", arrayExercise)
                        .addOnSuccessListener {
                            continuation.resume(Unit)
                        }
                        .addOnFailureListener {
                            continuation.resumeWithException(it)
                        }
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun uploadExercisePhoto(imgPath: Uri) = suspendCoroutine { continuation ->
        storage.getReference("images").child("image_${System.currentTimeMillis()}")
            .putFile(imgPath)
            .addOnSuccessListener { task ->
                task.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                    continuation.resume(it.toString())
                }
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    override suspend fun deleteExercise(workoutId: String, exercise: ExerciseDTO) =
        suspendCoroutine { continuation ->
            db.collection("workouts")
                .document(Firebase.auth.currentUser?.uid ?: return@suspendCoroutine)
                .collection("workouts")
                .document(workoutId)
                .get()
                .addOnSuccessListener { document ->
                    val arrayExercise =
                        document.toObject(WorkoutDTO::class.java)?.exercises?.toMutableList()
                            ?: mutableListOf()
                    arrayExercise.remove(exercise)
                    document.reference.update("exercises", arrayExercise)
                        .addOnSuccessListener {
                            continuation.resume(Unit)
                        }
                        .addOnFailureListener {
                            continuation.resumeWithException(it)
                        }
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun deleteWorkout(workoutId: String) = suspendCoroutine { continuation ->
        db.collection("workouts")
            .document(Firebase.auth.currentUser?.uid ?: return@suspendCoroutine)
            .collection("workouts")
            .document(workoutId)
            .delete()
            .addOnSuccessListener {
                continuation.resume(Unit)
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }
}