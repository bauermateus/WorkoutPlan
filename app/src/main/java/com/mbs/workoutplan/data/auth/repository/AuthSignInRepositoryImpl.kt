package com.mbs.workoutplan.data.auth.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthSignInRepositoryImpl : AuthSignInRepository {

    override suspend fun signIn(email: String, password: String) =
        suspendCoroutine { continuation ->
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    continuation.resume(true)
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
}
