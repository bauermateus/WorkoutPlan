package com.mbs.workoutplan.data.db.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mbs.workoutplan.data.db.models.UserData
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserInfoRepositoryImpl : UserInfoRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getUserInfo() = suspendCoroutine { continuation ->
        db.collection("users")
            .document("JJgpA0B9afhfhowURIV1")
            .get()
            .addOnSuccessListener {
                continuation.resume(
                    UserData(
                        it.getString("name") ?: ""
                    )
                )
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }


}