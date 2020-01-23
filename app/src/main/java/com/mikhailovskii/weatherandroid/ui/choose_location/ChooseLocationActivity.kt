package com.mikhailovskii.weatherandroid.ui.choose_location

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mikhailovskii.weatherandroid.R
import kotlinx.android.synthetic.main.activity_choose_location.*

class ChooseLocationActivity : AppCompatActivity(), ChooseLocationContract.ChooseLocationView {

    private val presenter = ChooseLocationPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_location)

        next_btn.setOnClickListener {
            when {
                country_tiet.text?.isBlank() == true -> {
                    country_til.error = getString(R.string.fill_the_field)
                    return@setOnClickListener
                }
                city_tiet.text?.isBlank() == true -> {
                    city_til.error = getString(R.string.fill_the_field)
                    return@setOnClickListener
                }
                else -> {
                    val location = "${country_tiet.text.toString()}, ${city_tiet.text.toString()}"
                    presenter.saveLocation(location)
                }
            }
        }

    }

    override fun onLocationSaved() {

    }

    override fun onLocationFailed() {

    }

}
