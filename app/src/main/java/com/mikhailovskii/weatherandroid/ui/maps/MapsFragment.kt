package com.mikhailovskii.weatherandroid.ui.maps


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.GradientDrawable
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
import java.util.*

class MapsFragment : Fragment(), MapsContract.MapsView {

    private lateinit var googleMap: GoogleMap
    private val presenter = MapsPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.attachView(this)
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(0xff69c8ea.toInt(), 0xff66c0e1.toInt())
        )

        scrollView.background = gradientDrawable

        city_et.setBackgroundColor(0xff69C0E6.toInt())

        initMapView(savedInstanceState)

        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val date = "${calendar.getDisplayName(
            Calendar.DAY_OF_WEEK,
            Calendar.LONG, Locale.getDefault()
        )}," +
                " ${calendar.get(Calendar.DATE)}." +
                "${calendar.get(Calendar.MONTH) + 1}." +
                "${calendar.get(Calendar.YEAR)}"

        date_tv.text = date

        city_et.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                val city = city_et.text

                val coord = getLocationFromAddress(city.toString())

                presenter.getDataByLocation(coord?.latitude!!, coord.longitude)

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coord))
                true
            } else {
                false
            }
        }

    }

    override fun onDataLoaded(result: String) {
        city_tv.text = result
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
