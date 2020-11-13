package com.university.softwaredesign_note.extensions

import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.university.softwaredesign_note.helper.StateChange

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