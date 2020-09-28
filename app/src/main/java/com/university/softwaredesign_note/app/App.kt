package com.university.softwaredesign_note.app

import android.app.Application
import com.university.softwaredesign_note.BuildConfig
import com.university.softwaredesign_note.cicerone.CiceroneHandler
import com.university.softwaredesign_note.timber.ReleaseTree
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
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
        return cicerone.navigatorHolder
    }

    override fun getCicerone(): Cicerone<Router> {
        return cicerone
    }

    fun getRouter(): Router {
        return cicerone.router
    }
}
