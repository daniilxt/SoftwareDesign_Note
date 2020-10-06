package com.university.softwaredesign_note

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.customBottomBar)

        val colors = intArrayOf(
            resources.getColor(android.R.color.holo_green_dark),
            resources.getColor(android.R.color.holo_green_dark)
        )
        val csl = ColorStateList(arrayOf(IntArray(0)), intArrayOf(colors[0]))


        val navController = findNavController(R.id.main_activity__container)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            Toast.makeText(this, "CLicked ", Toast.LENGTH_SHORT).show()
            // it.icon.colorFilter = PorterDuffColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
          //  it.icon = applicationContext.getDrawable(R.drawable.bottom_nav__add_btn)
            when (it.itemId) {

                R.id.firstFragment -> {
                    Toast.makeText(this, "MENU INF is SEARCH", Toast.LENGTH_SHORT).show()
                }
                R.id.secondFragment -> {
                    Toast.makeText(this, "MENU INF is Second", Toast.LENGTH_SHORT).show()

                }
                R.id.fourthFragment -> {

                }
                else -> {
                    Toast.makeText(
                        this,
                        "?? ${it.itemId}  ${R.drawable.bottom_nav__search_btn}",
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateNavigationBarState(it.itemId);
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        bottomNavigationView.itemRippleColor = csl
    }
/*    private fun updateNavigationBarState(actionId: Int) {
        val menu: Menu = bottomNavigationView.menu
        var i = 0
        val size: Int = menu.size()
        while (i < size) {
            val item: MenuItem = menu.getItem(i)
            item.isChecked = item.itemId == actionId
            i++
        }*/
}