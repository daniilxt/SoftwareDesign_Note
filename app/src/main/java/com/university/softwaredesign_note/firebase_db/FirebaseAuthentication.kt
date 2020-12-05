package com.university.softwaredesign_note.firebase_db

import android.app.Activity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.university.softwaredesign_note.app.App
import timber.log.Timber

object FirebaseAuthentication {
    private val auth = FirebaseAuth.getInstance()
    fun getUser(): FirebaseUser? {
        return auth.currentUser
    }
    fun signIn(email: String, password: String, callback: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback()
                    } else {
                        Toast.makeText(App.instance, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun register(login: String, password: String,callback:()->Unit) {

        auth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.i("CUUR USER is: ${auth.currentUser!!.uid}")
                        FirebaseDB.createUser(auth.currentUser!!.uid)
                        callback()
                    } else {
                        Toast.makeText(App.instance, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
    }
}