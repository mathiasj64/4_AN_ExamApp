package dk.cphbusiness.template

import android.app.Application

class App : Application() {
    companion object {
        lateinit var instance: App private set
        var permissionGranted: Boolean = false

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }



}