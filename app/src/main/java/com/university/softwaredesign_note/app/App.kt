package com.university.softwaredesign_note.app

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.university.softwaredesign_note.BuildConfig
import com.university.softwaredesign_note.cicerone.CiceroneHandler
import com.university.softwaredesign_note.timber.ReleaseTree
import timber.log.Timber


class App : Application(), CiceroneHandler {
    private lateinit var cicerone: Cicerone<Router>

    companion object {
        @JvmStatic
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        createCicerone()
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return String.format(
                        "Class:%s: Line: %s, Method: %s",
                        super.createStackElementTag(element),
                        element.lineNumber,
                        element.methodName
                    )
                }
            })
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    override fun createCicerone() {
        cicerone = Cicerone.create()
    }

    fun getNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

    override fun getCicerone(): Cicerone<Router> {
        return cicerone
    }

    fun getRouter(): Router {
        return cicerone.router
    }
}
