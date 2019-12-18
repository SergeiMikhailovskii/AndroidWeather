package com.mikhailovskii.weatherandroid.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.mikhailovskii.weatherandroid.R
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sign_in_btn.setOnClickListener {
            println(login_et.text.toString() + " " + password_et.text.toString())
        }


        // Facebook
        facebook_btn.setOnClickListener { fb_login_btn.performClick() }

        val callbackManager = CallbackManager.Factory.create()
        val email = "email"

        fb_login_btn.setReadPermissions(listOf(email))

        fb_login_btn.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Log.d("FBTAG", "facebook:onSuccess:$result")
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                Log.d("FBTAG", "facebook:onCancel")
                Toast.makeText(applicationContext, "Cancel", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException?) {
                Log.d("FBTAG", "facebook:onError", error)
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }

        })

        // Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        google_login_btn.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }

        google_btn.setOnClickListener {
            google_login_btn.performClick()
            Toast.makeText(applicationContext, "Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.i("GOOGLEAUTH", "Success")
        } catch (e: ApiException) {
            Log.i("GOOGLEAUTH", "Failed")
        }
    }

}
