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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.ui.main.MainActivity
import com.mikhailovskii.weatherandroid.util.toast
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.twitter.sdk.android.core.models.User
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : AppCompatActivity(), LoginContract.LoginView {

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var mAuth: FirebaseAuth? = null
    private val RC_SIGN_IN = 9001
    private var twitterAuthClient: TwitterAuthClient? = null
    private val presenter = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter.attachView(this)

        val callbackManager = CallbackManager.Factory.create()
        val email = "email"


        sign_in_btn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("login", login_et.text.toString())
            bundle.putString("password", password_et.text.toString())
            presenter.saveUserData(bundle)
        }

        // Facebook
        initFacebookAuthorization(email, callbackManager)

        // Google
        initGoogleAuthorization()

        // Twitter
        initTwitterAuthorization()

    }

    override fun onLoggedIn() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onLoginFailed() {
        toast("Login failed")
    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

        if (twitterAuthClient != null) {
            twitterAuthClient!!.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun initTwitterAuthorization() {
        val provider = OAuthProvider.newBuilder("twitter.com")
        provider.addCustomParameter("lang", "en")

        twitter_btn.setOnClickListener {
            val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(
                    TwitterAuthConfig(
                        resources.getString(R.string.twitter_api_key),
                        resources.getString(R.string.twitter_api_secret)
                    )
                )
                .debug(true)
                .build()
            Twitter.initialize(config)

            twitterAuthClient = TwitterAuthClient()

            val twitterActiveSession = TwitterCore.getInstance().sessionManager.activeSession

            if (twitterActiveSession == null) {
                twitterAuthClient!!.authorize(this, object : Callback<TwitterSession>() {
                    override fun success(result: Result<TwitterSession>?) {
                        val twitterSession = result?.data
                        getTwitterData(twitterSession)
                    }

                    override fun failure(exception: TwitterException?) {
                        Timber.e("Failed: ${exception?.message}")
                    }

                })
            } else {
                getTwitterData(twitterActiveSession)
            }

        }
    }

    private fun initGoogleAuthorization() {
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth!!.currentUser
        Timber.d(currentUser.toString())

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        google_btn.setOnClickListener {
            signIn()
        }
    }

    private fun initFacebookAuthorization(email: String, callbackManager: CallbackManager?) {
        facebook_btn.setOnClickListener { fb_login_btn.performClick() }

        fb_login_btn.setPermissions(listOf(email))

        fb_login_btn.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onCancel() {
                Timber.d("facebook:onCancel")
                toast("Cancel")
            }

            override fun onError(error: FacebookException?) {
                Timber.d("facebook:onError $error")
                toast("Error")
            }

        })
    }

    private fun getTwitterData(twitterSession: TwitterSession?) {
        val twitterApiClient = TwitterApiClient(twitterSession)
        val getUserCall = twitterApiClient.accountService.verifyCredentials(true, false, true)
        getUserCall.enqueue(object:Callback<User>() {
            override fun success(result: Result<User>?) {
                val socialId = result?.data?.id
                Timber.i("$socialId id")
            }

            override fun failure(exception: TwitterException?) {
                Timber.e("Failed: ${exception?.message}")
            }

        })
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            Toast.makeText(this, account.displayName.toString(), Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Not signed in", Toast.LENGTH_LONG).show()
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            updateUI(account)
        } catch (e: ApiException) {
            updateUI(null)
        }
    }

}
