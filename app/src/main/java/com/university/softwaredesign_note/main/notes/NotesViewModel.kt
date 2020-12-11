package com.university.softwaredesign_note.main.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.softwaredesign_note.app.App
import com.university.softwaredesign_note.firebase_db.FirebaseDB
import com.university.softwaredesign_note.models.Note
import timber.log.Timber
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class FirstFragmentViewModel : ViewModel() {
    enum class Status {
        LIKED,
        ARCHIVED,
        LIST
    }

    private var notes: MutableLiveData<ArrayList<Note>> = MutableLiveData()
    private var firebaseNotes: MutableLiveData<ArrayList<Note>> = MutableLiveData() //костыль, тут всегда полная копия заметок
    private var tmpNotes = notes.value
    private var event = Status.LIST

    init {
        FirebaseDB.initRepository {
            setData(it)
        }
    }

    fun setData(notesList: List<Note>) {
        firebaseNotes.postValue(notesList as ArrayList<Note>)

        when (event) {
            Status.LIST -> {
                tmpNotes = notes.value
               // firebaseNotes.postValue(notesList as ArrayList<Note>)
                val filteredList = notesList.stream().filter { !(it.private || it.archived) }
                        .collect(Collectors.toList())
                notes.postValue(filteredList as ArrayList<Note>?)
            }
            Status.LIKED -> {
                filterByLike()
            }
            Status.ARCHIVED -> {
                filterByArchive()
            }
        }
    }

    fun add(note: Note = Note.createNote()) { //по дефолту генерируется

        FirebaseDB.sendNote(note)
    }

    fun getLast(): Note? {
        return notes.value?.last()
    }

    fun getNotes(): LiveData<ArrayList<Note>> {
        //TODO help
        //сейчас сделано так, чтобы при null не возникло npe
        if (notes.value != null && tmpNotes == null) {
            Timber.i("GGGET ${notes.value}")
            Timber.i("GGGET ${tmpNotes}")
            tmpNotes = notes.value
            list()
        }
        return notes
    }

    fun search(line: String) {
        val tmp = firebaseNotes.value
        if (tmp != null) {
            val tmp2 = tmp.stream().filter { line in it.title.toLowerCase(Locale.ROOT) }
                    .collect(Collectors.toList())
            notes.postValue(tmp2 as ArrayList<Note>?)
        }
    }

    fun changeLikeState(position: Int) {
        val tmp = notes.value?.get(position)
        if (tmp != null) {
            tmp.liked = !tmp.liked
            notes.value?.set(position, tmp)
            FirebaseDB.sendNote(tmp)
        }
    }

    fun saveNote(note: Note) {
        //todo 2 if??
        val tmp = notes.value?.find { it -> it.id == note.id }
        val index = notes.value?.indexOf(tmp)
        if (index != null) {
            if (tmp != null) {
                notes.value?.set(index, tmp)
                FirebaseDB.sendNote(note)
            }
        }
    }

    fun filterByLike() {
        event = Status.LIKED
        val tmp = firebaseNotes.value
        if (tmp != null) {
            println(tmp)
            val tmp2 = tmp.stream().filter { it -> it.liked && !(it.private || it.archived) }
                    .collect(Collectors.toList())
            println(tmp)
            notes.postValue(tmp2 as ArrayList<Note>?)

        }
    }

    fun filterByArchive() {
        event = Status.ARCHIVED
        var tmp = firebaseNotes.value
        if (tmp != null) {
            val tmp2 = tmp.stream().filter { it.archived }.collect(Collectors.toList())
            notes.postValue(tmp2 as ArrayList<Note>?)
        }

    }

    fun list() {
        event = Status.LIST
        if (firebaseNotes.value != null)
        // filter notes without private mode or archive
        notes.postValue(firebaseNotes.value?.stream()?.filter { !(it.private || it.archived) }
                ?.collect(Collectors.toList()) as ArrayList<Note>)
    }

    fun delete(note: Note) {
        val tmp = notes.value?.find { it -> it.id == note.id }
        if (tmp != null) {
            FirebaseDB.deleteNote(note.id)
            notes.value?.remove(note)
            tmpNotes?.remove(tmp)
        }
    }
     fun update(){
        FirebaseDB.update {
            setData(it)
        }
    }
}