package com.university.softwaredesign_note.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.helper.CiceroneHelper
import com.university.softwaredesign_note.main.notes.NotesFragment
import com.university.softwaredesign_note.screens.Screens.AuthScreen
import com.university.softwaredesign_note.screens.Screens.HomeScreen
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private val navigator = object : AppNavigator(
        this,
        R.id.main_activity__fragment,
        supportFragmentManager
    ) {
        override fun setupFragmentTransaction(
            fragmentTransaction: FragmentTransaction,
            currentFragment: Fragment?,
            nextFragment: Fragment?
        ) {
            when (currentFragment) {
                is NotesFragment -> {
                    when (nextFragment) {
                        is EditorFragment -> {
                            fragmentTransaction.setCustomAnimations(
                                R.anim.appear_frg,
                                R.anim.fragment_fade_exit
                            )

                        }
                    }

                }
            }
            super.setupFragmentTransaction(fragmentTransaction, currentFragment, nextFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.i("ON CREATE")
        initFragment()

    }

    private fun initFragment() {
      //  CiceroneHelper.router().newRootScreen(HomeScreen())
        CiceroneHelper.router().newRootScreen(AuthScreen())
    }

    override fun onResume() {
        super.onResume()
        Timber.i("ON RESUME")
        CiceroneHelper.navHolder().setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        Timber.i("ON PAUSE")
        CiceroneHelper.navHolder().removeNavigator()
    }
}