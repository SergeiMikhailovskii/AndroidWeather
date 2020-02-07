package com.mikhailovskii.weatherandroid.ui.choose_location

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_choose_location.*
import org.koin.android.scope.currentScope

class ChooseLocationActivity : AppCompatActivity(), ChooseLocationContract.ChooseLocationView {

    private val presenter by currentScope.inject<ChooseLocationContract.ChooseLocationPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_location)

        presenter.attachView(this)

        next_btn.setOnClickListener {
            when {
                country_tiet.text == null || country_tiet.text?.isBlank() == true -> {
                    country_til.error = getString(R.string.empty_input)
                    return@setOnClickListener
                }
                city_tiet.text == null || city_tiet.text?.isBlank() == true -> {
                    city_til.error = getString(R.string.empty_input)
                    return@setOnClickListener
                }
                else -> {
                    val location =
                        "${city_tiet.text.toString()}, ${country_tiet.text.toString()}"
                    presenter.saveLocation(location)
                }
            }
        }

    }

    override fun onLocationSaved() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onLocationFailed() {

    }

}
