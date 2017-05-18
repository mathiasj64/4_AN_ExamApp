package dk.cphbusiness.template

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.*

class DBController(var context: Context = App.instance) : ManagedSQLiteOpenHelper(context, DBController.DB_NAME, null, DBController.DB_VERSION) {

    lateinit var db: SQLiteDatabase

    companion object {
        val DB_NAME = "TESTDB2"
        val DB_VERSION = 1
        val instance by lazy { DBController()
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun SQLcreateTable(db: SQLiteDatabase) {

        //Man kan også bruge ContentValues og db.insert()
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

    fun SQLaddLocation(db: SQLiteDatabase, locationName: String, latitude: String, longitude: String) {
        db.execSQL(
                "INSERT INTO locations (locationName, latitude, longitude)" +
                        "VALUES ('" + locationName + "', '" + latitude + "', '" + longitude + "')")
    }

    //SLET
/*    fun SQLGetLocation(db: SQLiteDatabase) : String {

        var locationID : String = ""

        var c : Cursor = db.rawQuery("SELECT locationID, locationName, latitude, longitude FROM locations", null)

        if(c.moveToFirst()){
            while (c.moveToNext()) {
                locationID += c.getString(0)
                locationID += c.getString(1)
                locationID += c.getString(2)
                locationID += c.getString(3)
            }
        }
        return locationID
    }*/

    fun getAdapterLocations(): List<Map<String, Any?>> {

        db = readableDatabase

        return db.select("locations").exec() {
            parseList(
                    object : MapRowParser<Map<String, Any?>> {
                        override fun parseRow(columns: Map<String, Any?>): Map<String, Any?> {
                            return columns
                        }
                    })
        }

        db.close()
    }

}
