package com.swyp.untitle.swyp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SWYPApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}