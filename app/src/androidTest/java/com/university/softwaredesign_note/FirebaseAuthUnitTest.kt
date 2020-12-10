package com.university.softwaredesign_note

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@RunWith(AndroidJUnit4::class)
class FirebaseAuthUnitTest {
    var auth = FirebaseAuthFun()

    @Test
    fun fir_register_smoke_create_and_delete_user_test() {
        val login = "testunit@ya.ru"
        val password = "123456"

        assertEquals(runBlocking {auth.createNewUser(login, password)}, true)
        assertEquals(runBlocking {auth.signIn(login, password)}, true)
        assertEquals(auth.getEmail(), login)
        assertEquals(runBlocking {auth.deleteUser(login, password)}, true)
        assertEquals(runBlocking {auth.signIn(login, password)}, false)
        assertEquals(auth.getEmail(), "")
    }

    @Test
    fun firebase_register_failed_login_already_exists_test() {
        val login = "testunit@ya.ru"
        val password = "123456"

        runBlocking {auth.createNewUser(login, password)}
        assertEquals(runBlocking {auth.createNewUser(login, password)}, false)
        assertEquals(runBlocking {auth.deleteUser(login, password)},true)
    }

    @Test
    fun firebase_register_failed_password_is_too_short_test() {
        val login = "testunit@ya.ru"
        val password = "12"

        assertEquals(runBlocking {auth.createNewUser(login, password)}, false)
    }

    @Test
    fun firebase_signIn_successfully_test() {
        val login = "testunit@ya.ru"
        val password = "123456"

        assertEquals(runBlocking {auth.createNewUser(login, password)}, true)
        assertEquals(runBlocking {auth.signIn(login, password)}, true)
        assertEquals(auth.getEmail(), login)
        assertEquals(runBlocking {auth.deleteUser(login, password)},true)
    }

    @Test
    fun firebase_signIn_failed_invalid_password_test() {
        val login = "testunit@ya.ru"
        val password = "1234564"

        assertEquals(runBlocking {auth.createNewUser(login, password)}, true)
        assertEquals(runBlocking {auth.signIn(login, password + "d")}, false)

        assertEquals(runBlocking {auth.deleteUser(login, password)},true)
    }

    @Test
    fun firebase_signIn_failed_login_is_not_exist_test() {
        val login = "testunit@ya.ru"
        val password = "1234564"

        assertEquals(runBlocking {auth.createNewUser(login, password)}, true)
        assertEquals(runBlocking {auth.deleteUser(login, password)},true)
        assertEquals(runBlocking {auth.signIn(login, password + "d")}, false)
    }
}