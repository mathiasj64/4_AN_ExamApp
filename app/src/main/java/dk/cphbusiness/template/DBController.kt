package dk.cphbusiness.template

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBController(var context: Context = App.instance) : ManagedSQLiteOpenHelper(context, DBController.DB_NAME, null, DBController.DB_VERSION) {

    lateinit var db: SQLiteDatabase

    companion object {
        val DB_NAME = "LocationDB"
        val DB_VERSION = 1
        val instance by lazy {
            DBController()
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun SQLcreateTable() {

        //Man kan også bruge ContentValues

        db = readableDatabase

        db.createTable("locations", true,
                "locationID" to INTEGER + PRIMARY_KEY,
                "locationName" to TEXT,
                "latitude" to TEXT,
                "longitude" to TEXT)

        /*db.execSQL("CREATE TABLE IF NOT EXISTS locations (" +
                "locationID     INTEGER     PRIMARY KEY," +
                "locationName   CHAR(50)," +
                "latitude       TEXT," +
                "longitude      TEXT" + ")")*/
    }

    fun SQLaddLocation(locationName: String, latitude: String, longitude: String) {
        db = readableDatabase
        db.execSQL(
                "INSERT INTO locations (locationName, latitude, longitude)" +
                        "VALUES ('" + locationName + "', '" + latitude + "', '" + longitude + "')")
    }

    fun SQLdeleteLocations() {
        db = readableDatabase
        db.execSQL(
                "DELETE from locations"
        )
    }

    fun getLocationList(): MutableList<Location> {

        db = readableDatabase

        val list = mutableListOf<Location>()

        var c: Cursor = db.rawQuery("SELECT locationName, latitude, longitude FROM locations", null)

        if (c.moveToFirst()) {
            while (c.moveToNext()) {
                list.add(Location(c.getString(0), c.getString(1).toDouble(), c.getString(2).toDouble()))
            }
        }
        return list
    }

    fun getAdapterLocations(): List<Map<String, Any?>> {

        db = readableDatabase

        return db.select("locations").exec {
            parseList(
                    object : MapRowParser<Map<String, Any?>> {
                        override fun parseRow(columns: Map<String, Any?>): Map<String, Any?> {
                            return columns
                        }
                    })
        }
    }

}
