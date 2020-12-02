package com.university.softwaredesign_note.models

import android.os.Parcelable
import com.university.softwaredesign_note.extensions.toFormat
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
        var id: Long,
        var noteText: String = "",
        var title: String = "",
        var liked: Boolean,
        var archived: Boolean,
        val private: Boolean,
        val date: Long,
        var dateEdit: Long
) :
        Parcelable {

    companion object {
        var id: Long = 0
        fun createNote(): Note {
            val date = Date().time
            val dateStr = Date((date)).toFormat(
                    "dd MMMM в HH:mm:ss",
                    Locale("ru")
            )
            return Note(id++, "tmp $id", "", false, false, false, date, date)
        }
    }
}