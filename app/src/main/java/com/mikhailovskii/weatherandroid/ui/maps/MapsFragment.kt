package com.mikhailovskii.weatherandroid.ui.maps


import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.mikhailovskii.weatherandroid.R
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment(), MapsContract.MapsView {

    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMapView(savedInstanceState)

        city_et.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                val city = city_et.text

                val coord = getLocationFromAddress(city.toString())

                if (coord != null) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(coord))
                }
                true
            } else {
                false
            }
        }

    }

    override fun onDataLoaded() {

    }

    override fun onLoadingFailed() {

    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }

    private fun initMapView(savedInstanceState: Bundle?) {
        map_view.onCreate(savedInstanceState)
        map_view.getMapAsync { googleMap ->
            run {
                this.googleMap = googleMap!!

                if (ActivityCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ), 100
                    )
                }

                googleMap.isIndoorEnabled = true

                val uiSettings = googleMap.uiSettings
                uiSettings.isIndoorLevelPickerEnabled = true
                uiSettings.isMapToolbarEnabled = true
                uiSettings.isCompassEnabled = true
                uiSettings.isZoomControlsEnabled = true

                val latLng = LatLng(40.0, (-74).toDouble())
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                googleMap.isMyLocationEnabled = true
                map_view.onResume()
            }
        }
    }

    private fun getLocationFromAddress(strAddress: String): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null

        address = coder.getFromLocationName(strAddress, 5)

        if (address == null) {
            return null
        }

        try {
            val location = address[0]
            p1 = LatLng(location.latitude, location.longitude)
        } catch (e: IndexOutOfBoundsException) {
            Toast.makeText(context, "City not found", Toast.LENGTH_SHORT).show()
        }

        return p1
    }

}
