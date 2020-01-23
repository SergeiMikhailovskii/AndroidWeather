package com.mikhailovskii.weatherandroid.ui.choose_location

import com.google.firebase.firestore.FirebaseFirestore
import com.mikhailovskii.weatherandroid.data.entities.User
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference

class ChooseLocationPresenter : ChooseLocationContract.ChooseLocationPresenter,
    BasePresenter<ChooseLocationContract.ChooseLocationView>() {

    private var database = FirebaseFirestore.getInstance()

    override fun saveLocation(location: String) {
        val user = Preference.user
        user?.location = location
        Preference.user = user

        saveLocationInFirebase(user)

        view?.onLocationSaved()
    }

    private fun saveLocationInFirebase(user: User?) {
        database.collection(USERS_COLLECTION).whereEqualTo(LOGIN_FIELD, user?.login).get()
            .addOnSuccessListener { databaseResult ->

                val map: Map<String, String> = hashMapOf(LOCATION to (user?.location ?: ""))

                for (document in databaseResult) {
                    database.collection(USERS_COLLECTION).document(document.id).update(map)
                }

            }
    }

    companion object {
        private const val USERS_COLLECTION = "users"
        private const val LOGIN_FIELD = "LOGIN"
        private const val LOCATION = "location"
    }

}