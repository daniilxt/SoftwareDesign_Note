package com.university.softwaredesign_note.helper

import android.view.View

abstract class StateChange : View.OnClickListener {
    private var isClicked = false
    abstract fun clickOn()
    abstract fun clickOff()

    override fun onClick(v: View?) {
        isClicked = !isClicked
        if (isClicked) {
            clickOn()
        } else {
            clickOff()
        }
    }
}
