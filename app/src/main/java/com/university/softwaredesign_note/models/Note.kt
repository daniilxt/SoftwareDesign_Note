package com.university.softwaredesign_note.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(var id: Long, var noteText: String = "", var title: String, var liked: Boolean) :
    Parcelable