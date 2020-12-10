package com.university.softwaredesign_note

import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthFun {
    public val auth = FirebaseAuth.getInstance()

    fun signOut() {
        auth.signOut()
    }

    suspend fun signIn(login: String, password: String): Boolean {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(login, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            continuation.resume(true)
                        } else {
                            continuation.resume(false)
                        }
                    }
        }
    }

    suspend fun createNewUser(login: String, password: String): Boolean {
        return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(login, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            continuation.resume(true)
                        } else {
                            continuation.resume(false)
                        }
                    }
        }
    }

    suspend fun deleteUser(login: String, password: String): Boolean {

        return suspendCoroutine { continuation ->
            auth.currentUser!!.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            continuation.resume(true)
                        } else {
                            continuation.resume(false)
                        }
                    }
        }
    }

    fun getEmail(): String {
        val user = auth.currentUser
        user.let {
            return user?.email ?: ""
        }
    }
}