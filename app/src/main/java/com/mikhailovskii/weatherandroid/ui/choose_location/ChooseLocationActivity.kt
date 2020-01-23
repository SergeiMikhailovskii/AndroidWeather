package com.mikhailovskii.weatherandroid.ui.choose_location

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mikhailovskii.weatherandroid.R

class ChooseLocationActivity : AppCompatActivity(), ChooseLocationContract.ChooseLocationView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_location)
    }

}
