package com.university.softwaredesign_note.cicerone

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router


interface CiceroneHandler {

    /**
     * This method must be called when parent object was created
     */
    fun createCicerone()

    fun router(): Router = getCicerone().router

    fun navHolder() = getCicerone().getNavigatorHolder()

    /**
     * @return Cicerone obj that was created at #createCicerone()
     */
    fun getCicerone(): Cicerone<Router>
}
