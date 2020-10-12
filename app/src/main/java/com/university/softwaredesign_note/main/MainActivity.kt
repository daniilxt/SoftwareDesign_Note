package com.university.softwaredesign_note.main

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.bus.Event
import com.university.softwaredesign_note.bus.EventBus
import com.university.softwaredesign_note.helper.CiceroneHelper
import com.university.softwaredesign_note.ui.notes.FirstFragmentViewModel
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.android.support.SupportAppNavigator


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var viewModel: FirstFragmentViewModel

    private var disposable: Disposable? = null

    private val navigator = SupportAppNavigator(
        this, supportFragmentManager,
        R.id.main_activity__container
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        disposable = EventBus.get().subscribe { obj ->
            when (obj) {
                Event.HIDE_BUTTON -> {
                    bottomNavigationView.visibility = View.GONE
                }
                Event.SHOW_BUTTON -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
        val colors = intArrayOf(
            ContextCompat.getColor(this, android.R.color.holo_green_dark),
            ContextCompat.getColor(this, android.R.color.holo_blue_dark)
        )
        val csl = ColorStateList(arrayOf(IntArray(0)), intArrayOf(colors[0]))


        bottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.main_activity__container)
        bottomNavigationView.setupWithNavController(navController)
/*
        bottomNavigationView.setOnNavigationItemSelectedListener {
            var screen: SupportAppScreen = FirstScreen()
            when (it.itemId) {

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
                    //it.isVisible = false
                    viewModel.add()
                    Toast.makeText(this, "fab", Toast.LENGTH_SHORT).show()

                    //updateNavigationBarState(it.itemId);
                }
            }
            CiceroneHelper.router().replaceScreen(screen)
            return@setOnNavigationItemSelectedListener true
        }*/
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.firstFragment -> {
                }
                R.id.secondFragment -> {

                }
                R.id.thirdFragment -> {
                }
                R.id.fourthFragment -> {
                }
                else -> {
                    //it.isVisible = false
                    viewModel.add()
                    Toast.makeText(this, "fab", Toast.LENGTH_SHORT).show()

                    //updateNavigationBarState(it.itemId);
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        bottomNavigationView.itemRippleColor = csl
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(FirstFragmentViewModel::class.java)

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