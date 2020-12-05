package com.university.softwaredesign_note.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.firebase_db.FirebaseAuthentication
import com.university.softwaredesign_note.helper.CiceroneHelper
import com.university.softwaredesign_note.screens.Screens.HomeScreen
import com.university.softwaredesign_note.screens.Screens.RegistrationScreen
import kotlinx.android.synthetic.main.auth_fragment.*

class AuthFragment : Fragment() {
    companion object {
        fun newInstance() = AuthFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.auth_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth_frg__signin.setOnClickListener {
            val login = auth_frg__login_et.text.toString().trim()
            val password = auth_frg__password_et.text.toString().trim()

            if (login.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuthentication.signIn(login, password) {
                    CiceroneHelper.router().replaceScreen(HomeScreen())
                }
            } else {
                auth_frg__login_et.error = "Check login"
            }
        }
        auth_frg__register.setOnClickListener {
            CiceroneHelper.router().navigateTo(RegistrationScreen())
        }
        //todo restore password
    }
}