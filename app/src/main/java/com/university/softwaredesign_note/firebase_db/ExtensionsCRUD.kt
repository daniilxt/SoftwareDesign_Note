package com.university.softwaredesign_note.firebase_db

import com.university.softwaredesign_note.models.Note

interface ExtensionsCRUD {
    fun getUserNotes(callback: (MutableList<Note>?) -> Unit)
}