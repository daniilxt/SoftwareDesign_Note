package com.university.softwaredesign_note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import meow.bottomnavigation.MeowBottomNavigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.customBottomBar)
        val navController = findNavController(R.id.main_activity__container)
        bottomNavigationView.setupWithNavController(navController)

//        bottomNavigationView.add(MeowBottomNavigation.Model(1, R.drawable.music))
      /*  bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.bottom_nav__like))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.bottom_nav__layers))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.bottom_nav__search))*/
    }
}