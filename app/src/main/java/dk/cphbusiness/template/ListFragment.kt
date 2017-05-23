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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.toast
import dk.cphbusiness.template.MapFragment

/**
 * Created by Philip on 18-05-2017.
 */

class ListFragment : Fragment() {

    var locationManager: LocationManager? = null

    val map = MapFragment()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        createTable()
        buttonCreateTable.setOnClickListener {
            toast("Table created")
            createTable()
        }

        buttonAddLocation.setOnClickListener {
            addLocation()
        }

        initFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    fun createTable() {
        Log.d("DebugDatabase", "DebugDatabaseAddress" + DebugDB.getAddressLog())

        val DBCtrl: DBController = DBController.instance


        DBCtrl.SQLcreateTable()

        updateList()
        //UPDATEMAP
    }

    fun addLocation() {
        Log.d("DebugDatabase", "DebugDatabaseAddress" + DebugDB.getAddressLog())

        if (locationManager == null) {
            //Måske tilføæj applicationcontext
            locationManager = App.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        var locationName: String = inputLocationName.getText().toString()

        var latitude: String = ""

        var longitude: String = ""


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

        var locationNameString: String = inputLocationName.getText().toString()

        var latitudeString: String = inputLatitude.getText().toString()

        var longitudeString: String = inputLongitude.getText().toString()

        //Kotlins koncatenation er bedre end Javas
        toast("Name: $locationName Latitude: $latitude Longitude: $longitude")

        DBCtrl.SQLaddLocation(locationName, latitude, longitude)



        updateList()
        //UPDATEMAP
    }

    fun updateList() {
        val DBCtrl: DBController = DBController.instance

        Log.d("DBController", "DBController test")

        Log.d("DBController", DBCtrl.getAdapterLocations().toString())

        listView1.adapter = LocationAdapter(DBCtrl.getAdapterLocations())
    }

    /*fun updateMap(locationName: String, latitude: Double, longitude: Double) {

        map.updateMap()
    }*/

    fun initFragment () {
        if(App.permissionGranted == true) {
            configureButton()
        }
        else {
            buttonOnce.text = "Location permission not granted"
        }
    }

    //På klik køres metoden der printer lokation ud
    private fun configureButton() {
        buttonOnce.setOnClickListener { getLocationOnce() }
    }

    private fun getLocationOnce() {

        if (locationManager == null) {
            //Måske tilføæj applicationcontext
            locationManager = App.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        if (locationManager!!.getLastKnownLocation("gps") == null) {
            val loc = locationManager!!.getLastKnownLocation("network")
            textViewOnce.text = (loc.longitude.toString() + ". " + loc.latitude + loc.provider).toString()
        } else {
            val loc2 = locationManager!!.getLastKnownLocation("gps")
            textViewOnce.text = (loc2.longitude.toString() + ". " + loc2.latitude + loc2.provider).toString()
        }

    }

}