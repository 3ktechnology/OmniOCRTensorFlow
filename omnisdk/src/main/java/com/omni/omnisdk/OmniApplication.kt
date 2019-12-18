package com.omni.omnisdk

import android.app.Application

class OmniApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {

        @get:Synchronized
        lateinit var instance: OmniApplication
    }

}