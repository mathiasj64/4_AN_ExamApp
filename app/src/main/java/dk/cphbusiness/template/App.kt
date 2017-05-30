package dk.cphbusiness.template

import android.app.Application

    //App indeholder state for applikationen
class App : Application() {
        //Instance er et companion object - man kan få fat på instance uden at have en instans af klassen App. Ligesom static virker i Java
    companion object {
        lateinit var instance: App private set
        //Setteren er privat og har default implementation

        //Bruges til at bestemme om brugeren har givet tilladelse til lokations-service
        var permissionGranted: Boolean = false

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

        // lateinit: initialiser en property, men dens state er endnu ikke tilgængelig..
        //           hvis værdi requestes før assignment --> exception
        //           lateinit kræver 'var'.. problem.. vi kan ændre værdien af instancen...
        //           løsning: 'private set'

        // delegate: properties med genbrugelig ædfærd.
        //           lazy, observable, values from a map

}