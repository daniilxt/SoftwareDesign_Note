package com.university.softwaredesign_note.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(val noteText:String, val liked:Boolean) : Parcelable