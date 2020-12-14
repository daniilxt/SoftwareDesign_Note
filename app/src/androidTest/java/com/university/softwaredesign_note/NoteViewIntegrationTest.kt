package com.university.softwaredesign_note

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.university.softwaredesign_note.main.notes.FirstFragmentViewModel
import com.university.softwaredesign_note.models.Note
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.BeforeClass
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@RunWith(AndroidJUnit4::class)
class NoteViewIntegrationTest () {
    var viewModel = FirstFragmentViewModel()
    var notes = viewModel.getNotes()

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

    suspend fun addNote(note: Note) : Boolean {
        return suspendCoroutine { continuation ->
            viewModel.add(note);
            continuation.resume(true)
        }
    }

    suspend fun deleteNote(note: Note) : Boolean {
        return suspendCoroutine { continuation ->
            viewModel.delete(note);
            continuation.resume(true)
        }
    }

    @Test
    fun fragment_view_model_add_Note_is_current_test() {
        val note = Note.createNote()
        assertEquals(auth.getEmail(), "testunit@ya.ru")

        viewModel.setData(arrayListOf(note))
        viewModel.add(note)
        viewModel.list()

        assertEquals(1, notes.value?.size)
        assertEquals(note, viewModel.getLast())
        viewModel.delete(note)

        assertEquals(0, notes.value?.size)
    }

    @Test
    fun fragment_view_model_change_like_status_is_current_test() {
        val initCount = notes.value?.size
        val note = Note.createNote()

        viewModel.setData(arrayListOf(note))
        viewModel.add(note)
        viewModel.list()

        viewModel.changeLikeState(initCount ?: 0)
        assertEquals(true, viewModel.getLast()?.liked)

        viewModel.changeLikeState(initCount ?: 0)
        assertEquals(false, viewModel.getLast()?.liked)

        viewModel.delete(note)
    }

    @Test
    fun fragment_view_model_search_is_current_test() {
        val initialNoteArray = notes.value
        for(i in 0..((initialNoteArray?.size) ?:0 -1)) {
            viewModel.delete(initialNoteArray!![i])
        }

        val count = 3
        var noteArray = ArrayList<Note>()
        val tittleArray = arrayListOf("tittle1", "middleTittle", "mineTittle")
        for(i in 0..count-1) {
            var note = Note.createNote()
            note.title = tittleArray[i]
            noteArray.add(note)
        }

        viewModel.setData(noteArray)

        for(i in 0..count-1) {
            viewModel.add(noteArray[i])
        }
        viewModel.list()

        viewModel.search("mi")
        viewModel.list()

        assertEquals(noteArray[1], notes.value?.get(0))
        assertEquals(noteArray[2], notes.value?.get(1))

        for(i in 0..count-1) {
            viewModel.delete(noteArray.get(i))
        }
    }

    @Test
    fun fragment_view_model_filter_by_like_is_current_test() {
        val initialNoteArray = notes.value
        for(i in 0..((initialNoteArray?.size) ?:0 -1)) {
            viewModel.delete(initialNoteArray!![i])
        }

        val count = 3
        var noteArray = arrayListOf(Note.createNote(), Note.createNote(), Note.createNote())
        viewModel.setData(noteArray)

        for(i in 0..count-1) {
            viewModel.add(noteArray[i])
        }
        viewModel.list()

        viewModel.changeLikeState(2)
        viewModel.list()
        val likedTrueNote = notes.value?.get(2)
        viewModel.filterByLike()
        viewModel.list()

        assertEquals(true, notes.value?.get(0)?.liked)
        assertEquals(likedTrueNote, notes.value?.get(0))

        for(i in 0..count-1) {
            viewModel.delete(noteArray.get(i))
        }
    }

    fun test_filter(type : filterType) {
        val initialNoteArray = notes.value
        for(i in 0..((initialNoteArray?.size) ?:0 -1)) {
            viewModel.delete(initialNoteArray!![i])
        }

        val count = 3
        var noteArray = arrayListOf(Note.createNote(), Note.createNote(), Note.createNote())
        viewModel.setData(noteArray)

        for(i in 0..count-1) {
            viewModel.add(noteArray[i])
        }
        viewModel.list()

        if (type == filterType.LIKE) {
            viewModel.changeLikeState(2)
            viewModel.list()
            val likedTrueNote = notes.value?.get(2)
            viewModel.filterByLike()
            viewModel.list()

            assertEquals(true, notes.value?.get(0)?.liked)
            assertEquals(likedTrueNote, notes.value?.get(0))
        }

        for(i in 0..count-1) {
            viewModel.delete(noteArray.get(i))
        }
    }

    enum class filterType{
        LIKE
    }
}