package dk.cphbusiness.template

import android.app.Fragment
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amitshekhar.DebugDB
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.toast

/**
 * Created by Philip on 18-05-2017.
 */

class ListFragment : Fragment() {

    var locationManager: LocationManager? = null

    val mainActivity = MainActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        createTable()

        initFragment()

        buttonDeleteLocations.setOnClickListener {
            val DBCtrl: DBController = DBController.instance
            DBCtrl.SQLdeleteLocations()
            updateList()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    fun createTable() {
        Log.d("DebugDatabase", "DebugDatabaseAddress" + DebugDB.getAddressLog())

        val DBCtrl: DBController = DBController.instance

        DBCtrl.SQLcreateTable()

        updateList()
    }

    //Henter lokation fra locationManager og navn fra textField og tilføjer til databasen
    fun addLocation() {

        if (locationManager == null) {
            locationManager = App.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        var locationName: String = inputLocationName.getText().toString()


        var longitude: String
        var latitude: String

        //Tjekker om der ikke er nogen lokation fra GPS-provideren.
        //(Der burde altid være en, da vi requester en ny lokation hvert sekund i locationListeneren)
        if (locationManager!!.getLastKnownLocation("gps") == null) {
            val loc = locationManager!!.getLastKnownLocation("network")
            latitude = loc.latitude.toString()
            longitude = loc.longitude.toString()
        } else {
            val loc = locationManager!!.getLastKnownLocation("gps")
            latitude = loc.latitude.toString()
            longitude = loc.longitude.toString()
        }

        val DBCtrl: DBController = DBController.instance

        //Kotlins koncatenation er bedre end Javas

        DBCtrl.SQLaddLocation(locationName, latitude, longitude)

        toast("Added: $locationName!")

        updateList()
    }

    //Opdaterer ListView
    fun updateList() {
        val DBCtrl: DBController = DBController.instance

        listView1.adapter = LocationAdapter(DBCtrl.getAdapterLocations())
    }

    //Tjekker om der er givet tilladelse til at få location
    fun initFragment() {
        if (App.permissionGranted == true) {
            configureButton()
        } else {
            toast("Location permission not granted")
        }
    }

    //På klik køres metoden der printer lokation ud
    private fun configureButton() {
        buttonAddLocation.setOnClickListener {
            addLocation()
        }
    }
}