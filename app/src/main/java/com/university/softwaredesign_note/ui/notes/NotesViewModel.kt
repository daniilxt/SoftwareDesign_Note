package com.university.softwaredesign_note.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.softwaredesign_note.models.Note
import timber.log.Timber
import java.text.FieldPosition
import java.util.stream.Collectors

class FirstFragmentViewModel : ViewModel() {
    private var notes: MutableLiveData<ArrayList<Note>> = MutableLiveData()
    private var tmpNotes = notes.value

    init {
        /*notes = MutableLiveData()*/
        notes.postValue(
            arrayListOf(
                Note("Hello", false),
                Note("World", false),
                Note("XT", false)
            )
        )
        Timber.i("ON VIEWMODEL INIT $notes")
    }

    fun add() {
        val tmp = notes.value
        tmp?.add(Note("tmp", true))
        notes.postValue(tmp)
    }

    fun getNotes(): LiveData<ArrayList<Note>> {
        tmpNotes = notes.value
        return notes
    }

    fun changeLikeState(position: Int){
        val tmp = notes.value?.get(position)
        if (tmp != null) {
            tmp.liked = !tmp.liked
            notes.value?.set(position,tmp)
        }
    }
    fun filterByLike() {
        tmpNotes = notes.value
        var tmp = notes.value
        if (tmp != null) {
            println(tmp)
            val tmp2 = tmp.stream().filter{ it -> it.liked }.collect(Collectors.toList())
            println(tmp)
            notes.postValue(tmp2 as ArrayList<Note>?)

        }
    }

    fun list() {
        notes.postValue(tmpNotes)
    }
}