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

        buttonAddLocation.setOnClickListener {
                addLocation()
            }

        buttonGetLocations.setOnClickListener {
            getLocations()
        }


        }
    }

    fun createTable() {
        Log.d("DebugDatabase", "DebugDatabaseAddress" + DebugDB.getAddressLog())

        val DBCtrl : DBController = DBController.instance

        val DB = DBCtrl.getWritableDatabase()

        DBCtrl.SQLcreateTable(DB)
    }

    fun addLocation() {
        Log.d("DebugDatabase", "DebugDatabaseAddress" + DebugDB.getAddressLog())

        val DBCtrl : DBController = DBController.instance

        val DB = DBCtrl.getWritableDatabase()

        var locationNameString: String = inputLocationName.getText().toString()

        var latitudeString: String = inputLocationName.getText().toString()

        var longtitudeString: String = inputLongitude.getText().toString()

        //Kotlins koncatenation er bedre end Javas
        toast("Name: $locationNameString Latitude: $latitudeString Longitude: $longtitudeString")

        DBCtrl.SQLaddLocation(DB, locationNameString, latitudeString, longtitudeString)
    }

    fun getLocations() {
        Log.d("DebugDatabase", "DegubDatabaseAddress" + DebugDB.getAddressLog())

        val DBCtrl : DBController = DBController.instance

        val DB = DBCtrl.getReadableDatabase()

        toast("testGET")

        textLocations.setText(DBCtrl.SQLGetLocation(DB))
    }

    }