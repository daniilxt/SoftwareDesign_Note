package com.university.softwaredesign_note.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

class DeleteableNote(
    var id: Long,
    var noteText: String = "",
    var title: String = "",
    var liked: Boolean,
    var archived: Boolean,
    val private: Boolean
) :
    Parcelable
