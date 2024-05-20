package com.brownx.runningapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * @author Andrew Brown
 * created on 17/05/2024
 */
@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())
    }
}