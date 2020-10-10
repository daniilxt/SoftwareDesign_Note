package com.university.softwaredesign_note

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.university.softwaredesign_note.helper.CiceroneHelper
import com.university.softwaredesign_note.screens.FirstScreen
import com.university.softwaredesign_note.screens.FourthScreen
import com.university.softwaredesign_note.screens.SecondScreen
import com.university.softwaredesign_note.screens.ThirdScreen
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private val navigator = SupportAppNavigator(
        this, supportFragmentManager, R.id.main_activity__container
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initScreen()
        bottomNavigationView = findViewById(R.id.customBottomBar)

        val colors = intArrayOf(
            ContextCompat.getColor(this,android.R.color.holo_green_dark),
            ContextCompat.getColor(this,android.R.color.holo_blue_dark)
        )
        val csl = ColorStateList(arrayOf(IntArray(0)), intArrayOf(colors[0]))


        val navController = findNavController(R.id.main_activity__container)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            var screen: SupportAppScreen = FirstScreen()
            when (it.itemId) {
                R.id.customBottomBar -> {
                }
                R.id.firstFragment -> {
                    screen = FirstScreen()
                }
                R.id.secondFragment -> {
                    screen = SecondScreen()
                }
                R.id.thirdFragment -> {
                    screen = ThirdScreen()
                }
                R.id.fourthFragment -> {
                    screen = FourthScreen()
                }
                else -> {
                    it.isVisible = false
                    Toast.makeText(this, "fab", Toast.LENGTH_SHORT).show()

                    //updateNavigationBarState(it.itemId);
                }
            }
            CiceroneHelper.router().replaceScreen(screen)
            return@setOnNavigationItemSelectedListener true
        }

        bottomNavigationView.itemRippleColor = csl
    }

    private fun initScreen() {
        CiceroneHelper.router().newRootScreen(FirstScreen())
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