package com.university.softwaredesign_note

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.university.softwaredesign_note.app.App
import com.university.softwaredesign_note.firebase_db.FirebaseAuthentication
import com.university.softwaredesign_note.firebase_db.FirebaseDB
import com.university.softwaredesign_note.models.Note
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@RunWith(AndroidJUnit4::class)
class FirebaseUnitTest {
    private var notes: MutableLiveData<ArrayList<Note>> = MutableLiveData()
    private var firebaseNotes: MutableLiveData<ArrayList<Note>> = MutableLiveData()
    private var tmpNotes = notes.value

    companion object {
        var auth = FirebaseAuthFun()

        var login = "testunit@ya.ru"
        var password = "123456"

        @BeforeClass @JvmStatic fun setup() {
            auth.signOut()
            runBlocking {auth.createNewUser(login, password)}
            runBlocking {auth.signIn(login, password)}
        }

        @AfterClass
        @JvmStatic fun teardown() {
            runBlocking {auth.deleteUser(login, password)}
        }
    }

    private suspend fun setData(notesList: List<Note>) :Boolean {
        return suspendCoroutine { continuation ->
            tmpNotes = notes.value
            firebaseNotes.postValue(notesList as ArrayList<Note>)
            continuation.resume(true)
        }
    }

    private suspend fun updateNotes() :Boolean {
        return suspendCoroutine { continuation ->
        if (firebaseNotes.value != null)
            notes.postValue(firebaseNotes.value?.stream()?.filter { !(it.private || it.archived) }
                    ?.collect(Collectors.toList()) as ArrayList<Note>)
            continuation.resume(true)
        }
    }

    private suspend fun addNote(note : Note) :Boolean {
        return suspendCoroutine { continuation ->
            FirebaseDB.sendNote(note)
            if (firebaseNotes.value != null) {
                var list = ArrayList<Note>(firebaseNotes.value!!)
                list.add(note)
                firebaseNotes.postValue(list)
            } else {
                firebaseNotes.postValue(arrayListOf(note))
            }
            continuation.resume(true)
        }
    }

    private suspend fun deleteNote(note : Note) :Boolean {
        return suspendCoroutine { continuation ->
            FirebaseDB.deleteNote(note.id)
            if (firebaseNotes.value != null) {
                var list = ArrayList<Note>(firebaseNotes.value!!)
                list.remove(note)
                firebaseNotes.postValue(list)
                continuation.resume(true)
            } else {
                continuation.resume(false)
            }
        }
    }

    @Test
    fun firebase_add_note_is_correct_test() {
        val note = Note.createNote()

        assertEquals(runBlocking {addNote(note)}, true)
        assertEquals(runBlocking {updateNotes()}, true)

        assertEquals(notes.value?.size, 1)
        val tmp = notes.value?.find { it -> it.id == note.id }
        val index = notes.value?.indexOf(tmp)

        val lastNote = notes.value?.get(index ?: 0)

        assertEquals(runBlocking {deleteNote(note)}, true)
        assertEquals(note, lastNote)
    }

    @Test
    fun firebase_delete_note_is_correct_test() {
        val count = 3
        val noteArray = ArrayList<Note>()
        for(i in 0..count-1) {
            val note = Note.createNote()
            noteArray.add(Note.createNote())
            assertEquals(runBlocking {addNote(note)}, true)
        }

        assertEquals(runBlocking {deleteNote(noteArray[1])}, true)
        assertEquals(runBlocking {updateNotes()}, true)

        assertEquals(notes.value?.size, 2)
        assertEquals(notes.value!![0], noteArray[0])
        assertEquals(notes.value!![1], noteArray[2])

        assertEquals(runBlocking {deleteNote(noteArray[0])}, true)
        assertEquals(runBlocking {deleteNote(noteArray[2])}, true)
    }
}