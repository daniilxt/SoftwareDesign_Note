package com.university.softwaredesign_note.firebase_db

import com.google.firebase.database.*
import com.university.softwaredesign_note.models.Note
import timber.log.Timber

object FirebaseDB : ExtensionsCRUD {
    private var usersTest = FirebaseDatabase.getInstance().getReference("TestUsers")
    private var usersRef = FirebaseDatabase.getInstance().getReference("users")
    private var id = 0;
    private val user = FirebaseAuthentication.getUser()

    override fun createUser(userID: String) {
        usersRef.child(userID).setValue(userID)
    }

    override fun sendNote(note: Note) {
        Timber.i("PATH is :: ${usersTest}   note is:: ${note}")
        usersTest.child(user!!.uid).child(note.id.toString()).setValue(note)
    }

    /**
     * Delete a note from user array notes.
     *
     * @param groupName Name of the future group.
     */
    override fun deleteNote(noteId: Long) {
        usersTest.child(user!!.uid).child(noteId.toString()).removeValue()
    }

    override fun initRepository(callback: (arr: List<Note>) -> Unit) {
        val notesListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val noteList: ArrayList<Note> = arrayListOf()  // очень важный массив, управляет данными
                    for (item in snapshot.children) {
                        val tmpItem = item.getValue(Note::class.java)
                        if (tmpItem != null) {
                            noteList.add(tmpItem)
                        }
                    }
                    callback(noteList)
                }
            }
        }
        usersTest.child(user!!.uid).addValueEventListener(notesListener)
    }

    /**
     * Update
     *
     * @param callBack Returned callback param of type MutableList<Note>
     * @return callback Return class MutableList<Note> with list of notes.
     */
//todo del

    fun update(callback: (arr: List<Note>) -> Unit) {
        usersTest
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val noteUpdateList: MutableList<Note> = mutableListOf()
                        if (snapshot.exists()) {
                            val noteList: ArrayList<Note> = arrayListOf()  // очень важный массив, управляет данными
                            for (item in snapshot.children) {
                                val tmpItem = item.getValue(Note::class.java)
                                if (tmpItem != null) {
                                    noteUpdateList.add(tmpItem)
                                }
                            }
                            callback(noteList)
                        }
                    }
                })
    }
}