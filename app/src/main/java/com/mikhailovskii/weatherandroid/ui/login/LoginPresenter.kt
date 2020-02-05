package com.mikhailovskii.weatherandroid.ui.login

import android.os.Bundle
import androidx.core.os.bundleOf
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.Profile
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.mikhailovskii.weatherandroid.data.entities.User
import com.mikhailovskii.weatherandroid.data.firebase.FirebaseLoginCallback
import com.mikhailovskii.weatherandroid.data.firebase.FirebaseModel
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference
import com.twitter.sdk.android.core.Result

class LoginPresenter : BasePresenter<LoginContract.LoginView>(), LoginContract.LoginPresenter,
    FirebaseLoginCallback {

    override fun saveUserData(bundle: Bundle) {

        val login = bundle.getString(LOGIN_KEY) ?: ""
        val password = bundle.getString(PASSWORD_KEY) ?: ""
        Preference.user = User(login = login, password = password)

        FirebaseModel().logInWithPassword(login, password, this)
    }

    override fun logInWithTwitter(result: Result<com.twitter.sdk.android.core.models.User>?) {
        val user = User(
            login = result?.data?.name,
            twitterKey = result?.data?.idStr,
            icon = result?.data?.profileImageUrl
        )

        FirebaseModel().logInWithTwitter(result, user, this)
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

                    FirebaseModel().logInWithFacebook(user, this)
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

        FirebaseModel().logInWithGoogle(user, this)
    }

    override fun checkUserLogged() {
        if (Preference.user != null) {
            if (Preference.user?.location?.isNotBlank() == true) {
                view?.onLoggedInWithFilledInfo()
            } else {
                view?.onLoggedInWithEmptyLocation()
            }
        }
    }

    override fun onLoggedInWithFilledInfo() {
        view?.onLoggedInWithFilledInfo()
    }

    override fun onLoggedInWithEmptyLocation() {
        view?.onLoggedInWithEmptyLocation()
    }

    override fun onLoginFailed() {
        view?.onLoginFailed()
    }

    companion object {
        private const val FB_ID_PERMISSION = "id"
        private const val FB_EMAIL_PERMISSION = "email"

        const val LOGIN_KEY = "login"
        const val PASSWORD_KEY = "password"
    }

}