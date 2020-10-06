package com.university.softwaredesign_note.screens

import androidx.fragment.app.Fragment
import com.university.softwaredesign_note.FirstFragment
import com.university.softwaredesign_note.FourthFragment
import com.university.softwaredesign_note.SecondFragment
import com.university.softwaredesign_note.ThirdFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class FirstScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return FirstFragment.newInstance()
    }
}

class SecondScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return SecondFragment.newInstance()
    }
}

class ThirdScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return ThirdFragment.newInstance()
    }
}
class FourthScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return FourthFragment.newInstance()
    }
}