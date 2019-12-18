package com.omniocr

import android.app.Application

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {

        @get:Synchronized
        lateinit var instance: MainApplication
    }

}