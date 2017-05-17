package dk.cphbusiness.template

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.amitshekhar.DebugDB
import kotlinx.android.synthetic.main.activity_java.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //message.text = "A Kotlin Activity"

        buttonCreateTable.setOnClickListener {
            toast("Table created")
            createTable()
        }

        buttonAddLocation.setOnClickListener {
            addLocation()
        }
    }

    fun createTable() {
        Log.d("DebugDatabase", "DebugDatabaseAddress" + DebugDB.getAddressLog())

        val DBCtrl: DBController = DBController.instance

        val DB = DBCtrl.getWritableDatabase()

        DBCtrl.SQLcreateTable(DB)

        updateList()
        //UPDATEMAP
    }

    fun addLocation() {
        Log.d("DebugDatabase", "DebugDatabaseAddress" + DebugDB.getAddressLog())

        val DBCtrl: DBController = DBController.instance

        val DB = DBCtrl.getWritableDatabase()

        var locationNameString: String = inputLocationName.getText().toString()

        var latitudeString: String = inputLatitude.getText().toString()

        var longitudeString: String = inputLongitude.getText().toString()

        //Kotlins koncatenation er bedre end Javas
        toast("Name: $locationNameString Latitude: $latitudeString Longitude: $longitudeString")

        DBCtrl.SQLaddLocation(DB, locationNameString, latitudeString, longitudeString)

        updateList()
        //UPDATEMAP
    }

    /*fun getLocations() {
        Log.d("DebugDatabase", "DegubDatabaseAddress" + DebugDB.getAddressLog())

        val DBCtrl: DBController = DBController.instance

        val DB = DBCtrl.getReadableDatabase()

        toast("testGET")

        textLocations.setText(DBCtrl.SQLGetLocation(DB))

        updateList()
        //UPDATEMAP
    }*/

    fun updateList() {
        val DBCtrl: DBController = DBController.instance

        Log.d("DBController", "DBController test")

        Log.d("DBController", DBCtrl.getAdapterLocations().toString())

        listView1.adapter = LocationAdapter(DBCtrl.getAdapterLocations())
    }

}