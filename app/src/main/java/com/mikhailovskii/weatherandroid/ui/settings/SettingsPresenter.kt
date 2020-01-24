package com.mikhailovskii.weatherandroid.ui.settings

import com.google.firebase.firestore.FirebaseFirestore
import com.mikhailovskii.weatherandroid.data.entities.User
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference

class SettingsPresenter : BasePresenter<SettingsContract.SettingsView>(),
    SettingsContract.SettingsPresenter {

    private var database = FirebaseFirestore.getInstance()

    override fun saveEditedUserInfo(user: User?) {
        saveUserDataToFirebase(user)
        saveUserDataToPreferences(user)
        view?.onUserDataSaved()
    }

    override fun getInitialUserData() {
        val user = Preference.user
        view?.onInitialUserDataLoaded(user)
    }

    private fun saveUserDataToPreferences(user: User?) {
        Preference.user = user
    }

    private fun saveUserDataToFirebase(user: User?) {
        val oldUser = Preference.user

        database.collection(USERS_COLLECTION).whereEqualTo(LOGIN_FIELD, oldUser?.login).get()
            .addOnSuccessListener { databaseResult ->

                val map: Map<String, Any> = hashMapOf(
                    LOGIN_FIELD to (user?.login ?: ""),
                    FACEBOOK_KEY_FIELD to (user?.facebookKey ?: ""),
                    GOOGLE_KEY_FIELD to (user?.googleKey ?: ""),
                    ICON_FIELD to (user?.icon ?: ""),
                    LOCATION_FIELD to (user?.location ?: ""),
                    PASSWORD_FIELD to (user?.password ?: ""),
                    PREFERRED_UNIT_FIELD to (user?.preferredUnit ?: 0),
                    TWITTER_KEY_FIELD to (user?.twitterKey ?: "")
                )

                for (document in databaseResult) {
                    database.collection(USERS_COLLECTION).document(document.id).update(map)
                }
            }

    }

    companion object {
        private const val USERS_COLLECTION = "users"
        private const val LOGIN_FIELD = "login"
        private const val FACEBOOK_KEY_FIELD = "facebookKey"
        private const val GOOGLE_KEY_FIELD = "googleKey"
        private const val ICON_FIELD = "icon"
        private const val LOCATION_FIELD = "location"
        private const val PASSWORD_FIELD = "password"
        private const val PREFERRED_UNIT_FIELD = "preferredUnit"
        private const val TWITTER_KEY_FIELD = "twitterKey"
    }

}