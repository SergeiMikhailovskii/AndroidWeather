package com.mikhailovskii.weatherandroid.ui.login

import android.os.Bundle
import androidx.core.os.bundleOf
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.Profile
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.firestore.FirebaseFirestore
import com.mikhailovskii.weatherandroid.data.entities.User
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference
import com.twitter.sdk.android.core.Result

class LoginPresenter : BasePresenter<LoginContract.LoginView>(), LoginContract.LoginPresenter {

    private var database = FirebaseFirestore.getInstance()

    override fun saveUserData(bundle: Bundle) {

        val login = bundle.getString(LOGIN_KEY)
        val password = bundle.getString(PASSWORD_KEY)

        val user = User(login = login, password = password)

        database.collection("users").document().set(user)

        Preference.user = user
        view?.onLoggedIn()

    }

    override fun logInWithTwitter(result: Result<com.twitter.sdk.android.core.models.User>?) {
        val user = User(
            login = result?.data?.name,
            twitterKey = result?.data?.idStr,
            icon = result?.data?.profileImageUrl
        )

        Preference.user = user

        view?.onLoggedIn()
    }

    override fun logInWithFacebook(result: LoginResult?) {
        val request =
            GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken()) { _, response ->

                if (response.jsonObject != null && Profile.getCurrentProfile() != null) {
                    val data = response.jsonObject
                    var id: String? = ""
                    var icon: String? = ""
                    val name: String? = Profile.getCurrentProfile().name

                    if (data.has(FB_ID_PERMISSION)) {
                        id = response.jsonObject.getString(FB_ID_PERMISSION)
                        icon = "https://graph.facebook.com/v2.2/$id/picture?height=120&type=normal"
                    }

                    val user = User(login = name, facebookKey = id, icon = icon)

                    Preference.user = user

                    view?.onLoggedIn()

                }
            }

        val parameters = bundleOf("fields" to FB_EMAIL_PERMISSION)
        request.parameters = parameters
        request.executeAsync()
    }

    override fun logInWithGoogle(result: GoogleSignInAccount?) {
        val user = User(
            login = result?.displayName,
            googleKey = result?.idToken,
            icon = result?.photoUrl.toString()
        )

        Preference.user = user

        view?.onLoggedIn()
    }

    override fun checkUserLogged() {
        if (Preference.user != null) {
            view?.onLoggedIn()
        }
    }

    companion object {
        private const val FB_ID_PERMISSION = "id"
        private const val FB_EMAIL_PERMISSION = "email"

        const val LOGIN_KEY = "login"
        const val PASSWORD_KEY = "password"
    }

}