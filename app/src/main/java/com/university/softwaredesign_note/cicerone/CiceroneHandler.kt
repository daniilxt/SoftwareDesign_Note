package com.university.softwaredesign_note.cicerone


import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

interface CiceroneHandler {

    /**
     * This method must be called when parent object was created
     */
    fun createCicerone()

    fun router(): Router = getCicerone().router

    fun navHolder() = getCicerone().navigatorHolder

    /**
     * @return Cicerone obj that was created at #createCicerone()
     */
    fun getCicerone(): Cicerone<Router>
}
