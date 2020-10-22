package com.university.softwaredesign_note.screens

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.university.softwaredesign_note.main.EditorFragment
import com.university.softwaredesign_note.main.HomeFragment
import com.university.softwaredesign_note.models.Note


/*

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
}*/
object Screens {
    fun EditorScreen(note: Note) = FragmentScreen("EditorFragment") {

        val frg = EditorFragment.newInstance()
        val bundle = Bundle()
        bundle.putParcelable("note", note)
        frg.arguments = bundle
        frg
    }

    fun HomeScreen() = FragmentScreen("HomeScreen") { HomeFragment() }
}