package com.university.softwaredesign_note.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.firebase_db.FirebaseAuthentication
import com.university.softwaredesign_note.firebase_db.FirebaseDB
import com.university.softwaredesign_note.helper.CiceroneHelper
import com.university.softwaredesign_note.screens.Screens.HomeScreen
import kotlinx.android.synthetic.main.auth_fragment.*
import kotlinx.android.synthetic.main.registration_fragment.*

class RegistrationFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrationFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reg_frg__signin.setOnClickListener {
            val login = reg_frg__login_tv.text.toString().trim()
            val password = reg_frg__password_et.text.toString().trim()
            val passwordConfirm = reg_frg__password_confirm_et.text.toString().trim()

            if (login.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty()) {
                if (password == passwordConfirm) {
                    FirebaseAuthentication.register(login, password){

                        CiceroneHelper.router().replaceScreen(HomeScreen())
                    }
                } else {
                    Toast.makeText(requireContext(), "Passwords don't match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Input all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}