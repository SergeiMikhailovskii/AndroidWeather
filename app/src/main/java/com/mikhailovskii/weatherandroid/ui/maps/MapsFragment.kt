package com.mikhailovskii.weatherandroid.ui.maps


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.mikhailovskii.weatherandroid.R
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment() {

    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.getMapAsync { googleMap ->
            run {
                this.googleMap = googleMap!!

                val latLng = LatLng(40.0, (-74).toDouble())
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                map_view.onResume()
            }
        }
    }
}
