package com.university.softwaredesign_note.firebase_db

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthentication {
    private val auth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String, callback: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback()
                    }
                }
    }
}