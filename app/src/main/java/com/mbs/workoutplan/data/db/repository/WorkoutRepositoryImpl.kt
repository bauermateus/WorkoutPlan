package com.mbs.workoutplan.data.db.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mbs.workoutplan.data.db.models.UserData
import com.mbs.workoutplan.data.db.models.UserInfoResponse
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WorkoutRepositoryImpl: WorkoutRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getUserInfo() = suspendCoroutine { continuation ->
        db.collection("users")
            .document("JJgpA0B9afhfhowURIV1")
            .addSnapshotListener { value, error ->
                if (value != null) {
                    continuation.resume(
                        UserInfoResponse(
                            data = UserData(
                                name = value.getString("name") ?: ""
                            )
                        )
                    )
                } else {
                    continuation.resume(UserInfoResponse(error = error))
                }
            }
    }


}