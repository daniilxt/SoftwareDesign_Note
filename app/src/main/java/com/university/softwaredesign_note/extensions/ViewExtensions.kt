package com.university.softwaredesign_note.extensions

import android.widget.ImageButton
import com.university.softwaredesign_note.helper.StateChange
import java.text.SimpleDateFormat
import java.util.*

/**
 * Allows you to perform actions when the button clicked
 *
 * @param clickOn The function that performs something after the button clicked on.
 * @param clickOff The function that performs something before the button clicked off.
 */
fun ImageButton.setCustomOnClickListener(clickOn: () -> Unit, clickOff: () -> Unit) {
    setOnClickListener(object : StateChange() {
        override fun clickOn() {
            clickOn()
        }

        override fun clickOff() {
            clickOff()
        }
    })
}

/**
 * Shows the colored ImageView during time
 *
 * @param format The pattern for formatting date.
 * @param locale What language will be used with pattern.
 */
fun Date.toFormat(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

/**
 * Shows date in format 02 января в 15:33:36
 *
 */
fun Date.toStrDate():String {
    return Date(this.time).toFormat("dd MMMM в HH:mm:ss",
            Locale("ru"))
}