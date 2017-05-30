package dk.cphbusiness.template

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.location_list.*


/**
 * Created by Mathias on 16-05-2017.
 */
// Bliver attached på listView1 med data fra databasen.
class LocationAdapter(var listLocation: List<Map<String, Any?>>) : BaseAdapter() {


    override fun getView(position: Int, view: View?, viewgroup: ViewGroup): View {

        val viewLocationList = LayoutInflater.from(App.instance).inflate(R.layout.location_list, viewgroup, false);


        val textViewLocationName = viewLocationList.findViewById(R.id.textView1) as TextView
        val textViewLatitude = viewLocationList.findViewById(R.id.textView2) as TextView
        val textViewLongitude = viewLocationList.findViewById(R.id.textView3) as TextView


        val item = getItem(position)

        textViewLocationName.text = item["locationName"].toString()
        textViewLatitude.text = item["latitude"].toString()
        textViewLongitude.text = item["longitude"].toString()


        return viewLocationList
    }

    override fun getItem(position: Int): Map<String, Any?> {
        return listLocation.get(position)
    }

    override fun getCount(): Int {
        return listLocation.size
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).get("locationID") as Long;
    }
}