package com.university.softwaredesign_note.firebase_db

import com.google.firebase.database.FirebaseDatabase
import com.university.softwaredesign_note.models.Note

class FirebaseDB:ExtensionsCRUD {
    private var usersList = FirebaseDatabase.getInstance().getReference("users")
    //todo
   // private var currentUser = FirebaseAuth.
    override fun getUserNotes(callback: (MutableList<Note>?) -> Unit) {
       // usersList.child()

    }
}