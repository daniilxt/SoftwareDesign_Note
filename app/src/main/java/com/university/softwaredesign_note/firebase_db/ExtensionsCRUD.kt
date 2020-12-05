package com.university.softwaredesign_note.firebase_db

import com.university.softwaredesign_note.models.Note

interface ExtensionsCRUD {
    fun initRepository(callback: (arr: List<Note>) -> Unit)
    fun createUser(userID: String)
    fun sendNote(note: Note)
    fun deleteNote(noteId: Long)
}