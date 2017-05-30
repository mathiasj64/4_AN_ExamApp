package dk.cphbusiness.template

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBController(var context: Context = App.instance) : ManagedSQLiteOpenHelper(context, DBController.DB_NAME, null, DBController.DB_VERSION) {
    //Extender en abstrakt klasse som har nogle metoder

    lateinit var db: SQLiteDatabase

    companion object {
        val DB_NAME = "TESTDB2"
        val DB_VERSION = 1
        val instance by lazy {
            DBController()
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        //Her kunne man have kørt SQL der laver tabellerne istedet for i mainActivitys onCreate
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    //Opretter forbindelse til databasen og laver databasen hvis den ikke allerede er der
    fun SQLcreateTable() {

        db = readableDatabase

        db.createTable("locations", true,
                "locationID" to INTEGER + PRIMARY_KEY,
                "locationName" to TEXT,
                "latitude" to TEXT,
                "longitude" to TEXT)
    }

    //Tilføjer en lokation til databasen
    fun SQLaddLocation(locationName: String, latitude: String, longitude: String) {

        //Opretter en database eller åbner forbindelse til eksisterende db
        db = readableDatabase
        db.execSQL(
                "INSERT INTO locations (locationName, latitude, longitude)" +
                        "VALUES ('" + locationName + "', '" + latitude + "', '" + longitude + "')")
    }

    //Sletter alle lokationer i databasen
    fun SQLdeleteLocations() {
        db = readableDatabase
        db.execSQL(
                "DELETE from locations"
        )
    }

    //Henter alle lokationer ud fra databsen
    //Bruges til at hente alle lokationer ud til at sætte markører på kortet
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

    //Henter all lokationer så de kan bruges i ListAdapteren til ListView
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
