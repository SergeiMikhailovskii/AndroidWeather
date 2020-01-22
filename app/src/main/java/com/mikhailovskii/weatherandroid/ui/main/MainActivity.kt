package com.mikhailovskii.weatherandroid.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.ui.forecast.ForecastFragment
import com.mikhailovskii.weatherandroid.ui.maps.MapsFragment
import com.mikhailovskii.weatherandroid.ui.settings.SettingsFragment
import com.mikhailovskii.weatherandroid.ui.shop.ShopFragment
import com.mikhailovskii.weatherandroid.util.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            replaceFragment(ForecastFragment(), R.id.fragment_layout)
        }

        bottom_nav.setOnNavigationItemSelectedListener { menuItem ->
            var fragment: Fragment? = null

            when (menuItem.itemId) {
                R.id.forecast_item -> {
                    menuItem.isChecked = true
                    fragment = ForecastFragment()
                }
                R.id.shop_item -> {
                    menuItem.isChecked = true
                    fragment = ShopFragment()
                }
                R.id.map_item -> {
                    menuItem.isChecked = true
                    fragment = MapsFragment()
                }
                R.id.settings_item -> {
                    menuItem.isChecked = true
                    fragment = SettingsFragment()
                }
            }

            if (fragment != null) {
                replaceFragment(fragment, R.id.fragment_layout)
            }

            false
        }
    }
}
