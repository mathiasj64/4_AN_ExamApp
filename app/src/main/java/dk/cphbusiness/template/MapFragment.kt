package dk.cphbusiness.template

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by Philip on 18-05-2017.
 */

class MapFragment : Fragment(), OnMapReadyCallback {

    internal var mGoogleMap: GoogleMap? = null
    internal var mMapView: MapView? = null
    internal var mView: View? = null
    var DBController = DBController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_map, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // mView!! betyder at mView ikke må returneres som null
        mMapView = mView!!.findViewById(R.id.map) as MapView
        if (mMapView != null) {
            mMapView!!.onCreate(null)
            mMapView!!.onResume()
            mMapView!!.getMapAsync(this)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(context)

        mGoogleMap = googleMap
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)

        //Sætter en nål ved skolen
        googleMap.addMarker(MarkerOptions().position(LatLng(55.769942, 12.511579)).title("CPH Business Academy").snippet("Min skole"))

        //Sætter kameraet til at starte ved nålen med den rigtige zoom.
        val Skolen = CameraPosition.builder().target(LatLng(55.769942, 12.511579)).zoom(12F).bearing(0F).tilt(0F).build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Skolen))

        //Looper igennem alle lokationer i databasen og sætter en nål for hver lokation i databasen.
        var locList : MutableList<Location> = DBController.getLocationList()

        for(i in locList) {
            googleMap.addMarker(MarkerOptions().position(LatLng(i.latitude, i.longitude)).title(i.name))
        }


    }
}
