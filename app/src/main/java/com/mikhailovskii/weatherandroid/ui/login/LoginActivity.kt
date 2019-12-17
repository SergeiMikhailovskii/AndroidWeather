package com.mikhailovskii.weatherandroid.ui.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.mikhailovskii.weatherandroid.R
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sign_in_btn.setOnClickListener {
            println(login_et.text.toString() + " " + password_et.text.toString())
        }

        facebook_btn.setOnClickListener { fb_login_btn.performClick() }

        val callbackManager = CallbackManager.Factory.create()
        fb_login_btn.setReadPermissions("email", "public_profile")
        fb_login_btn.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(result: LoginResult?) {
                Log.d("FBTAG", "facebook:onSuccess:$result")
            }

            override fun onCancel() {
                Log.d("FBTAG", "facebook:onCancel")
            }

            override fun onError(error: FacebookException?) {
                Log.d("FBTAG", "facebook:onError", error)
            }

        })

    }


}
