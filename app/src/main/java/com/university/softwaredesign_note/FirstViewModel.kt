package com.university.softwaredesign_note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.softwaredesign_note.models.Note

class FirstFragmentViewModel : ViewModel() {
    var notes: MutableLiveData<List<Note>> = MutableLiveData()

    fun init() {
        notes = MutableLiveData()
    }
}