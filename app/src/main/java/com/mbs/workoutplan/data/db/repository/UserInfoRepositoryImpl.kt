package com.mbs.workoutplan.data.db.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mbs.workoutplan.data.db.models.UserDataDTO
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserInfoRepositoryImpl : UserInfoRepository {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    override suspend fun getUserInfo() = suspendCoroutine { continuation ->
        db.collection("users")
            .document(currentUserUid ?: return@suspendCoroutine)
            .get()
            .addOnSuccessListener {
                continuation.resume(
                    it.toObject(UserDataDTO::class.java)
                )
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    override suspend fun uploadUserImg(imgPath: Uri) = suspendCoroutine { continuation ->
        storage.getReference("users").child("profile${currentUserUid ?: return@suspendCoroutine}")
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

    override suspend fun updateUserInfo(userData: UserDataDTO) = suspendCoroutine { continuation ->
        db.collection("users")
            .document(currentUserUid ?: return@suspendCoroutine)
            .get()
            .addOnSuccessListener { document ->
                (if (userData.image == null) {
                    document.reference.update(
                        "name", userData.name
                    )
                } else document.reference.update(
                    "name", userData.name,
                    "image", userData.image
                )
                        ).addOnSuccessListener {
                        continuation.resume(Unit)
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }
}