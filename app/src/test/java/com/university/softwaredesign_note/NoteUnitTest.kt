package com.university.softwaredesign_note
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp
import com.university.softwaredesign_note.firebase_db.FirebaseDB
import com.university.softwaredesign_note.main.notes.FirstFragmentViewModel
import com.university.softwaredesign_note.models.Note
import org.junit.Test
import org.junit.Assert.*
import org.junit.BeforeClass
import java.text.SimpleDateFormat
import java.util.*

class NoteUnitTest : AppCompatActivity() {
    private var notes: MutableLiveData<ArrayList<Note>> = MutableLiveData()
    private var tmpNotes = notes.value

    @Test
    fun note_to_string_method_is_correct() {
        var note = Note.createNote()
        val date = note.date
        note.noteText = "noteText"
        note.title = "noteTitle"

        val string = "Note(id=$date, noteText='noteText', " +
                "title='noteTitle', liked=false, archived=false, private=false, date=$date, dateEdit=$date)"
        assert(note.toString().equals(string))
    }
}