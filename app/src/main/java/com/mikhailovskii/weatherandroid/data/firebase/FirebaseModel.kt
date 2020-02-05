package com.mikhailovskii.weatherandroid.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.mikhailovskii.weatherandroid.data.entities.StickerPack
import com.mikhailovskii.weatherandroid.data.entities.User
import com.twitter.sdk.android.core.Result
import timber.log.Timber

class FirebaseModel {

    private val db = FirebaseFirestore.getInstance()

    fun getStickersCollectionList(callback: FirebaseDataCallback<ArrayList<StickerPack>>?) {
        db.collection("stickers").get().addOnSuccessListener { result ->
            val stickerPackList = ArrayList<StickerPack>()

            result.forEach { stickerPackList.add(it.toObject(StickerPack::class.java)) }

            callback?.onFirebaseDataLoaded(stickerPackList)
        }.addOnFailureListener {
            callback?.onFirebaseDataFailed()
        }

    }

    fun saveUserLocation(user: User?) {
        db.collection(USERS_COLLECTION).whereEqualTo(LOGIN_FIELD, user?.login).get()
            .addOnSuccessListener { databaseResult ->

                val map: Map<String, String> = hashMapOf(LOCATION to (user?.location ?: ""))

                databaseResult.forEach {
                    db.collection(USERS_COLLECTION).document(it.id).update(map)
                }

            }
    }

    fun logInWithPassword(login: String, password: String, callback: FirebaseLoginCallback?) {
        db.collection(USERS_COLLECTION).get().addOnSuccessListener { result ->
            result.forEach { document ->
                var user = document.toObject(User::class.java)

                if (user.login == login && user.password == password) {
                    user = User(login = login, password = password)

                    if (user.location?.isNotBlank() == true) {
                        callback?.onLoggedInWithFilledInfo()
                    } else {
                        callback?.onLoggedInWithEmptyLocation()
                    }
                }
            }
        }.addOnFailureListener {
            callback?.onLoginFailed()
        }
    }

    fun logInWithTwitter(
        result: Result<com.twitter.sdk.android.core.models.User>?,
        user: User,
        callback: FirebaseLoginCallback?
    ) {
        logInWithSocialNetwork(user, callback) { databaseUser ->
            databaseUser.login == result?.data?.name
                    && databaseUser.twitterKey == result?.data?.idStr
                    && databaseUser.icon == result?.data?.profileImageUrl
        }
    }

    fun logInWithFacebook(user: User, callback: FirebaseLoginCallback?) {
        logInWithSocialNetwork(user, callback) { databaseUser ->
            databaseUser.login == user.login
                    && databaseUser.facebookKey == user.facebookKey
                    && databaseUser.icon == user.icon
        }

    }

    fun logInWithGoogle(user: User, callback: FirebaseLoginCallback?) {
        logInWithSocialNetwork(user, callback) { databaseUser ->
            databaseUser.login == user.login
                    && databaseUser.googleKey == user.googleKey
                    && databaseUser.icon == user.icon
        }
    }

    private fun logInWithSocialNetwork(
        user: User,
        callback: FirebaseLoginCallback?,
        checkUser: (user: User) -> Boolean
    ) {
        db.collection(USERS_COLLECTION).get().addOnSuccessListener { databaseResult ->
            var isUserPresent = false

            databaseResult.forEach { document ->

                if (checkUser(document.toObject(User::class.java))) {
                    isUserPresent = true
                    return@forEach
                }
            }

            if (!isUserPresent) {
                db.collection(USERS_COLLECTION).document().set(user)
                    .addOnSuccessListener {
                        Timber.d("Social info saved")
                    }.addOnFailureListener { e ->
                        Timber.e("Error twitter save: $e")
                    }
            }

            if (user.location?.isNotBlank() == true) {
                callback?.onLoggedInWithFilledInfo()
            } else {
                callback?.onLoggedInWithEmptyLocation()
            }

        }.addOnFailureListener {
            callback?.onLoginFailed()
        }
    }


    fun getStickerPackByName(name: String, callback: FirebaseDataCallback<StickerPack>?) {
        db.collection(STICKERS_COLLECTION)
            .whereEqualTo(TITLE_FIELD, name)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    result.forEach { document ->
                        val stickerPack = document.toObject(StickerPack::class.java)
                        callback?.onFirebaseDataLoaded(stickerPack)
                    }
                } else {
                    callback?.onFirebaseDataFailed()
                }
            }
            .addOnFailureListener {
                callback?.onFirebaseDataFailed()
            }
    }

    companion object {
        private const val USERS_COLLECTION = "users"
        private const val STICKERS_COLLECTION = "stickers"
        private const val TITLE_FIELD = "title"
        private const val LOGIN_FIELD = "LOGIN"
        private const val LOCATION = "location"

        const val TWITTER = "TWITTER"
        const val FACEBOOK = "FACEBOOK"
        const val GOOGLE = "GOOGLE"
    }

}