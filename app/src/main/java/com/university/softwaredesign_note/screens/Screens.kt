package com.university.softwaredesign_note.screens

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.university.softwaredesign_note.main.AuthFragment
import com.university.softwaredesign_note.main.EditorFragment
import com.university.softwaredesign_note.main.HomeFragment
import com.university.softwaredesign_note.main.RegistrationFragment
import com.university.softwaredesign_note.models.Note

object Screens {
    fun EditorScreen(note: Note) = FragmentScreen("EditorFragment") {

        val frg = EditorFragment.newInstance()
        val bundle = Bundle()
        bundle.putParcelable("note", note)
        frg.arguments = bundle
        frg
    }

    fun HomeScreen() = FragmentScreen("HomeScreen") { HomeFragment() }
    fun AuthScreen() = FragmentScreen("AuthScreen") { AuthFragment() }
    fun RegistrationScreen() = FragmentScreen("RegistrationScreen") { RegistrationFragment() }
}