package com.swyp.moodit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MooditApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}