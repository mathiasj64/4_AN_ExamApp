package dk.cphbusiness.template

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : Activity() {

    val db = DBController()
    var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main)

        db.SQLcreateTable()

        if (savedInstanceState == null)
        {
            fragmentManager.beginTransaction().replace(R.id.contentFrame, MapFragment()).commit()
        }

        //Sætter onClickListeners på knapperne til at skifte fragments
        goToMap.setOnClickListener { replaceWithMap() }
        goToList.setOnClickListener { replaceWithList() }


        //Tjekker om man behøver at få tilladelse til lokations-tjenester (APK over 23). Hvis APK er over 23 skal man bruge tilladelse, vi tjekker derfor om der er givet tilladelse.
        //Hvis der ikke er tilladelse beder vi brugeren om tilladelse, kun hvis brugeren giver tilladelse bruger vi lokations-tjenester.

        //Tjekker om Android versionen er over eller lig med 23 (M for Marshmallow = APK 23)
        //Hvis APK er under 23 behøver man ikke tilladelse til lokation-services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Hvis FINE og COARSE adgang ikke er givet bliver det requested. Hvis man får adgang bliver permissionGranted sat til true.
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Requester adgang fra brugeren
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET), 10)
                //Dette kalder onRequestPermissionResult med koden 10, hvilket vi har valgt skal representere denne kombination af tilladelser (FINE, COARSE og INTERNET).
                return
            } else {
                App.permissionGranted = true
            }
        } else {
            App.permissionGranted = true
        }

        locationListener = object : LocationListener {
        //Vi angiver ikke at der skal ske noget ved locationChanged da den sidste nye lokation bliver hentet i ListFragment når den skal bruges.
            override fun onLocationChanged(location: Location) {
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            //Hvis tilladelse til lokations-tjenester bliver slået fra bliver der kørt et intent hvor burgeren kan få lov til at slå tilladelsen til igen.
            override fun onProviderDisabled(provider: String) {
                toast("Provider disabled")
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }

        //Tjekker om der er en locationManager, hvis ikke bliver der lavet en.
        if (locationManager == null) {
            locationManager = App.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        //Beder om lokationsopdateringer hvert sekund på locationManager. Vi prøver både gps og netværk.
        locationManager!!.requestLocationUpdates("network", 1000, 0F, locationListener)
        locationManager!!.requestLocationUpdates("gps", 1000, 0F, locationListener)
    }

    //Får tilbagemelding fra request permissions-metoden
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
        //Case 10 er en kombination af permission requests.
            10 -> {
                //Hvis der er givet adgang sætter vi permissionGranted til true, som senere bliver brugt til at tjekke om man får lov til at hente lokationer.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    App.permissionGranted = true
                return
            }
        }
    }

    //Sætter listen ind på fragmentet.
    fun replaceWithList()
    {
        fragmentManager.beginTransaction().replace(R.id.contentFrame, ListFragment()).commit()
    }

    //Sætter kortet ind på fragmentet.
    fun replaceWithMap() {
        fragmentManager.beginTransaction().replace(R.id.contentFrame, MapFragment()).commit()
    }
}