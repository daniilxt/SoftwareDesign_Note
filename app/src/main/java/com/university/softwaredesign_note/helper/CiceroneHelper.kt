package com.university.softwaredesign_note.helper

import com.university.softwaredesign_note.app.App

object CiceroneHelper {
    fun router() = App.instance.router()
    fun navHolder() = App.instance.navHolder()
}
