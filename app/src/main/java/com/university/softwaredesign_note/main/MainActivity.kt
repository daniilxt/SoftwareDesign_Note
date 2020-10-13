package com.university.softwaredesign_note.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.helper.CiceroneHelper
import com.university.softwaredesign_note.screens.HomeScreen
import ru.terrakok.cicerone.android.support.SupportAppNavigator


class MainActivity : AppCompatActivity() {

    private val navigator = SupportAppNavigator(
        this, supportFragmentManager,
        R.id.main_activity__fragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
    }

    private fun initFragment() {
        CiceroneHelper.router().newRootScreen(HomeScreen())
    }

    override fun onResume() {
        super.onResume()
        CiceroneHelper.navHolder().setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        CiceroneHelper.navHolder().removeNavigator()
    }
}