package com.mikhailovskii.weatherandroid.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mikhailovskii.weatherandroid.R
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sign_in_btn.setOnClickListener {
            println(login_et.text.toString() + " " + password_et.text.toString())
        }
    }
}
