package com.mikhailovskii.weatherandroid.ui.login

import android.os.Bundle
import androidx.core.os.bundleOf
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.Profile
import com.facebook.login.LoginResult
import com.google.firebase.database.*
import com.mikhailovskii.weatherandroid.AndroidWeatherApp
import com.mikhailovskii.weatherandroid.data.entities.User
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference
import com.twitter.sdk.android.core.Result
import timber.log.Timber

class LoginPresenter : BasePresenter<LoginContract.LoginView>(), LoginContract.LoginPresenter {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var query: Query

    override fun saveUserData(bundle: Bundle) {

        val login = bundle.getString("login")
        val password = bundle.getString("password")

        val user = User(login = login, password = password)

        query = database.child("users").orderByChild("login").equalTo(login)
        query.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Timber.e(error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    val users = snapshot.children

                    users.forEach { data ->
                        val userData = data.getValue(User::class.java)
                        Timber.i("LOGIN ${userData?.login}")
                    }

                    Preference.getInstance(AndroidWeatherApp.appContext).user = user

                    view?.onLoggedIn()

                } else {
                    Timber.e("NO SUCH USER $login $password")

                    database.child("users").push().setValue(user)

                    view?.onLoginFailed()
                }

            }

        })

    }

    override fun logInWithTwitter(result: Result<com.twitter.sdk.android.core.models.User>?) {
        val user = User(login = result?.data?.name, twitterKey = result?.data?.idStr, icon = result?.data?.profileImageUrl)
        database.child("users").push().setValue(user)

        Preference.getInstance(AndroidWeatherApp.appContext).user = user

        view?.onLoggedIn()
    }

    override fun logInWithFacebook(result: LoginResult?) {
        val request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken()){_, response ->

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
                database.child("users").push().setValue(user)

                Preference.getInstance(AndroidWeatherApp.appContext).user = user

                view?.onLoggedIn()

            }
        }

        val parameters = bundleOf("fields" to FB_EMAIL_PERMISSION)
        request.parameters = parameters
        request.executeAsync()
    }

    companion object {
        private const val FB_ID_PERMISSION = "id"
        private const val FB_EMAIL_PERMISSION = "email"
    }

}