package com.university.softwaredesign_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.softwaredesign_note.models.Note

class FirstFragmentViewModel : ViewModel() {
    var notes: MutableLiveData<ArrayList<Note>> = MutableLiveData()

    init {
        notes = MutableLiveData()
        notes.postValue(
            arrayListOf(
                Note("helo", false),
                Note("wrld", false), Note
                    ("test", false)
            )
        )
    }

    fun add() {
        val tmp = notes.value
        tmp?.add(Note("tmp", false))
        notes.postValue(tmp)
    }

    fun getNotes(): LiveData<ArrayList<Note>> {
        return notes
    }
}