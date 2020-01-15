package com.mikhailovskii.weatherandroid.ui.login

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.ui.main.MainActivity
import com.mikhailovskii.weatherandroid.util.showErrorToast
import com.mikhailovskii.weatherandroid.util.showInfoToast
import com.mikhailovskii.weatherandroid.util.showWarningToast
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

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(
                ContextCompat.getColor(applicationContext, R.color.forecastGradientTopLeft),
                ContextCompat.getColor(applicationContext, R.color.forecastGradientBottomRight)
            )
        )

        scrollView.background = gradientDrawable

        sign_in_btn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(LoginPresenter.LOGIN_KEY, login_til.text.toString())
            bundle.putString(LoginPresenter.PASSWORD_KEY, password_et.text.toString())
            presenter.saveUserData(bundle)
        }

        // Facebook
        initFacebookAuthorization()

        // Google
        initGoogleAuthorization()

        // Twitter
        initTwitterAuthorization()

        presenter.checkUserLogged()
    }

    override fun onLoggedIn() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onLoginFailed() {
        showErrorToast("Login failed")
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

    private fun initFacebookAuthorization() {
        val callbackManager = CallbackManager.Factory.create()
        val email = "email"

        facebook_btn.setOnClickListener { fb_login_btn.performClick() }

        fb_login_btn.setPermissions(listOf(email))

        fb_login_btn.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(result: LoginResult?) {
                presenter.logInWithFacebook(result)
            }

            override fun onCancel() {
                Timber.d("facebook:onCancel")
                showWarningToast("Cancel")
            }

            override fun onError(error: FacebookException?) {
                Timber.e("facebook:onError $error")
                showErrorToast("Error")
            }

        })
    }

    private fun getTwitterData(twitterSession: TwitterSession?) {
        val twitterApiClient = TwitterApiClient(twitterSession)
        val getUserCall = twitterApiClient.accountService.verifyCredentials(true, false, true)
        getUserCall.enqueue(object : Callback<User>() {

            override fun success(result: Result<User>?) {
                val socialId = result?.data?.id
                Timber.i("$socialId id")
                presenter.logInWithTwitter(result)
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
            showInfoToast(account.displayName.toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            showWarningToast("Not signed in")
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        presenter.logInWithGoogle(completedTask.result)

    }

}
