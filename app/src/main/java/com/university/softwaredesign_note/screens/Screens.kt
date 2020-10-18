package com.university.softwaredesign_note.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.university.softwaredesign_note.ui.notes.NotesFragment
import com.university.softwaredesign_note.ui.FourthFragment
import com.university.softwaredesign_note.SecondFragment
import com.university.softwaredesign_note.ThirdFragment
import com.university.softwaredesign_note.main.EditorFragment
import com.university.softwaredesign_note.main.HomeFragment
import com.university.softwaredesign_note.models.Note
import ru.terrakok.cicerone.android.support.SupportAppScreen

class FirstScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return NotesFragment.newInstance()
    }
}

class SecondScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return SecondFragment.newInstance()
    }
}

class ThirdScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return ThirdFragment.newInstance()
    }
}

class FourthScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return FourthFragment.newInstance()
    }
}

class EditorScreen(private val note: Note) : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        val frg = EditorFragment.newInstance()
        val bundle = Bundle()
        bundle.putParcelable("note", note)
        frg.arguments = bundle
        return frg
    }
}

class HomeScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return HomeFragment.newInstance()
    }
}