package com.mikhailovskii.weatherandroid.ui.login

import android.os.Bundle
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

                } else {
                    Timber.e("NO SUCH USER $login $password")

                    database.child("users").push().setValue(user)
                }

            }

        })

        Preference.getInstance(AndroidWeatherApp.appContext).user = user

        view?.onLoggedIn()

    }

    override fun logInWithTwitter(result: Result<com.twitter.sdk.android.core.models.User>?) {
        val user = User(login = result?.data?.email.let { "" }, twitterKey = result?.data?.idStr)
        database.child("users").push().setValue(user)

        view?.onLoggedIn()
    }

}