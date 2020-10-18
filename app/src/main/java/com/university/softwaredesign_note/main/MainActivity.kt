package com.university.softwaredesign_note.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.helper.CiceroneHelper
import com.university.softwaredesign_note.screens.HomeScreen
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private val navigator = SupportAppNavigator(
        this, supportFragmentManager,
        R.id.main_activity__fragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.i("ON CREATE")
        initFragment()

    }

    private fun initFragment() {
        CiceroneHelper.router().newRootScreen(HomeScreen())
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