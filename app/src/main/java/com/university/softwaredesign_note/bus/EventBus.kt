package com.university.softwaredesign_note.bus

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

object EventBus {
    private val bus = PublishSubject.create<Any>()

    fun send(event: Any?) {
        event?.let {
            bus.onNext(it)
        }
    }

    @JvmStatic
    fun get(): Observable<Any> {
        return bus.subscribeOn(AndroidSchedulers.mainThread())
            .doOnError { error ->
                var message: String? = "Something went wrong"
                if (error.message != null) {
                    message = error.message
                }
                Timber.d(message)
            }
    }
}