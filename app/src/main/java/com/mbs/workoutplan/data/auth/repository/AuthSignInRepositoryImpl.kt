package com.mbs.workoutplan.data.auth.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mbs.workoutplan.data.auth.SignInResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthSignInRepositoryImpl() : AuthSignInRepository {

    override suspend fun signIn(email: String, password: String): SignInResult {
        return suspendCoroutine { continuation ->
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                continuation.resume(
                    SignInResult(
                        success = result.user != null,
                        error = null
                    )
                )
            }.addOnFailureListener { exception ->
                continuation.resume(
                    SignInResult(
                        success = false,
                        error = exception
                    )
                )
            }
        }
    }
}
