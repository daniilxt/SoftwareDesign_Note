package com.university.softwaredesign_note.main.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.softwaredesign_note.models.Note
import timber.log.Timber
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class FirstFragmentViewModel : ViewModel() {
    private var notes: MutableLiveData<ArrayList<Note>> = MutableLiveData()
    private var tmpNotes = notes.value
    private var id = 4L

    init {
        //todo data provider
        val date = Date().time
        tmpNotes =
            arrayListOf(
                Note(1, "Hello", "", false, false, false,date,date),
                Note(2, "World", "", false, true, true,date,date),
                Note(3, "XT", "", false, false, false,date,date)
            )
        notes.postValue(tmpNotes?.stream()?.filter { !(it.private || it.archived) }
            ?.collect(Collectors.toList()) as ArrayList<Note>)
        Timber.i("ON VIEWMODEL INIT $notes")
    }

    fun add(note:Note = Note.createNote()) { //по дефолту генерируется
        val tmp = notes.value
        tmp?.add(note)
        tmpNotes?.add(note)
        notes.postValue(tmp)
    }

    fun getLast():Note?{
        return notes.value?.last()
    }
    fun getNotes(): LiveData<ArrayList<Note>> {
        return notes
    }

    fun changeLikeState(position: Int) {
        val tmp = notes.value?.get(position)
        if (tmp != null) {
            tmp.liked = !tmp.liked
            notes.value?.set(position, tmp)
        }
    }

    fun saveNote(note: Note) {
        //todo 2 if??
        val tmp = notes.value?.find { it -> it.id == note.id }
        val index = notes.value?.indexOf(tmp)
        if (index != null) {
            if (tmp != null) {
                notes.value?.set(index, tmp)
            }
        }
    }

    fun filterByLike() {
        val tmp = tmpNotes
        if (tmp != null) {
            println(tmp)
            val tmp2 = tmp.stream().filter { it -> it.liked && !(it.private || it.archived) }
                .collect(Collectors.toList())
            println(tmp)
            notes.postValue(tmp2 as ArrayList<Note>?)

        }
    }

    fun filterByArchive() {
        var tmp = tmpNotes
        if (tmp != null) {
            println(tmp)
            val tmp2 = tmp.stream().filter { it.archived }.collect(Collectors.toList())
            println(tmp)
            notes.postValue(tmp2 as ArrayList<Note>?)
        }
    }

    fun list() {
        // filter notes without private mode or archive
        notes.postValue(tmpNotes?.stream()?.filter { !(it.private || it.archived) }
            ?.collect(Collectors.toList()) as ArrayList<Note>)
    }

    fun delete(note: Note) {
        val tmp = notes.value?.find { it -> it.id == note.id }
        if (tmp != null) {
            notes.value?.remove(note)
            tmpNotes?.remove(tmp)
        }
    }
}