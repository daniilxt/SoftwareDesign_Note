package com.university.softwaredesign_note.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    var id: Long,
    var noteText: String = "",
    var title: String = "",
    var liked: Boolean,
    var archived: Boolean,
    val private: Boolean
) :
    Parcelable{

    companion object{
        var id:Long = 0
        fun createNote():Note{
            return  Note(id++, "tmp $id", "", false, false, false)
        }
    }
}

/*
Timber.i("${tmpNotes?.stream()?.filter { (!it.private xor it.archived) }
?.collect(Collectors.toList())}")*/
