package dk.cphbusiness.template

import android.app.Fragment
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

class ListFragment : Fragment()
{

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

        val DBCtrl: DBController = DBController.instance

        var locationNameString: String = inputLocationName.getText().toString()

        var latitudeString: String = inputLatitude.getText().toString()

        var longitudeString: String = inputLongitude.getText().toString()

        //Kotlins koncatenation er bedre end Javas
        toast("Name: $locationNameString Latitude: $latitudeString Longitude: $longitudeString")

        DBCtrl.SQLaddLocation(locationNameString, latitudeString, longitudeString)



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

}