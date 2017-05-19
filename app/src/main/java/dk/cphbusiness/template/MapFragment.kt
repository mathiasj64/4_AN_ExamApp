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
        googleMap.addMarker(MarkerOptions().position(LatLng(55.794614, 12.461796)).title("Virum").snippet("2830 SWAG"))
        val Virum = CameraPosition.builder().target(LatLng(55.794614, 12.461796)).zoom(16F).bearing(0F).tilt(0F).build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Virum))
    }
}
