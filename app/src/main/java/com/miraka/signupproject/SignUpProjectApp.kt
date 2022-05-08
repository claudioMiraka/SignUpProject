package com.miraka.signupproject

import android.app.Application
import timber.log.Timber

class SignUpProjectApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}