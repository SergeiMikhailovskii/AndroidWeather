package com.mikhailovskii.weatherandroid.ui.login

import android.os.Bundle
import com.google.firebase.database.*
import com.mikhailovskii.weatherandroid.AndroidWeatherApp
import com.mikhailovskii.weatherandroid.data.entities.User
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference
import timber.log.Timber

class LoginPresenter : BasePresenter<LoginContract.LoginView>(), LoginContract.LoginPresenter {

    private lateinit var database: DatabaseReference
    private lateinit var query: Query

    override fun saveUserData(bundle: Bundle) {
        database = FirebaseDatabase.getInstance().reference

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

}