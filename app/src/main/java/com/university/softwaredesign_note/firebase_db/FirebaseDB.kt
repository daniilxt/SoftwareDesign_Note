package com.university.softwaredesign_note.firebase_db

import com.google.firebase.database.FirebaseDatabase
import com.university.softwaredesign_note.models.Note
import timber.log.Timber

object FirebaseDB:ExtensionsCRUD {
    private var usersTest = FirebaseDatabase.getInstance().getReference("TestUsers")
    private var groupsRef = FirebaseDatabase.getInstance().getReference("GROUP")
    private var id = 0;

    //todo
   // private var currentUser = FirebaseAuth.
    override fun getUserNotes(callback: (MutableList<Note>?) -> Unit) {
       // usersList.child()
    }
    fun sendNote(note: Note){
        Timber.i("PATH is :: ${usersTest}   note is:: ${note}")
        usersTest.child((note.id).toString()).setValue(note)
    }

    /**
     * Creates a new group from name.
     *
     * @param groupName Name of the future group.
     */
     fun createGroup(groupName: String) {
        if (groupName.isNotEmpty()) {
           // groupsRef.child(groupName).child(user!!.uid).setValue(user!!.uid)
        }
    }
    /**
     * Delete a note from user array notes.
     *
     * @param groupName Name of the future group.
     */
     fun deleteNote(noteId: Long) {
        usersTest.child(noteId.toString()).removeValue()
    }
}