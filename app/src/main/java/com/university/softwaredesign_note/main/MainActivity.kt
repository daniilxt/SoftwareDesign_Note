package com.university.softwaredesign_note.main

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
                    val slideUp: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_to_bot)

                    if (bottomNavigationView.visibility == View.VISIBLE) {
                        bottomNavigationView.visibility = View.GONE
                        bottomNavigationView.startAnimation(slideUp)
                    }
                }
                Event.SHOW_BUTTON -> {
                    val slideUp: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_from_bot)

                    if (bottomNavigationView.visibility == View.GONE) {
                        bottomNavigationView.visibility = View.VISIBLE
                        bottomNavigationView.startAnimation(slideUp)
                    }
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

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.firstFragment -> {
                    viewModel.list()
                }
                R.id.secondFragment -> {

                }
                R.id.thirdFragment -> {
                    viewModel.sortByLike()
                }
                R.id.fourthFragment -> {
                }
                else -> {
                    viewModel.add()
                    Toast.makeText(this, "fab", Toast.LENGTH_SHORT).show()
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