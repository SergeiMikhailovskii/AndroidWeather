package com.mikhailovskii.weatherandroid.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.ui.maps.MapsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_nav.setOnNavigationItemSelectedListener { menuItem ->
            var fragment: Fragment? = null
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            when(menuItem.itemId) {
                R.id.map_item -> {
                    menuItem.isChecked = true
                    fragment = MapsFragment()
                }
            }

            if (fragment != null) {
                fragmentTransaction.replace(R.id.fragment_layout, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction.commit()
            }

            false
        }
    }
}
