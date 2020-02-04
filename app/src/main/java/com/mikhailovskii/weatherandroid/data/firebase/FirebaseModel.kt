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

            result.forEach { document ->
                val stickerPack = document.toObject(StickerPack::class.java)
                stickerPackList.add(stickerPack)
            }

            callback?.onFirebaseDataLoaded(stickerPackList)
        }.addOnFailureListener {
            callback?.onFirebaseDataFailed()
        }

    }

    fun saveUserLocation(user: User?) {
        db.collection(USERS_COLLECTION).whereEqualTo(LOGIN_FIELD, user?.login).get()
            .addOnSuccessListener { databaseResult ->

                val map: Map<String, String> = hashMapOf(LOCATION to (user?.location ?: ""))

                databaseResult.forEach { document ->
                    db.collection(USERS_COLLECTION).document(document.id).update(map)
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

    fun logInWithSocialNetwork(
        social: String,
        user: User,
        callback: FirebaseLoginCallback?,
        result: Result<com.twitter.sdk.android.core.models.User>?
    ) {
        db.collection(USERS_COLLECTION).get().addOnSuccessListener { databaseResult ->
            var isUserPresent = false

            databaseResult.forEach { document ->
                val databaseUser = document.toObject(User::class.java)

                val twitterCondition = databaseUser.login == result?.data?.name
                        && databaseUser.twitterKey == result?.data?.idStr
                        && databaseUser.icon == result?.data?.profileImageUrl

                val facebookCondition = databaseUser.login == user.login
                        && databaseUser.facebookKey == user.facebookKey
                        && databaseUser.icon == user.icon

                val googleCondition = databaseUser.login == user.login
                        && databaseUser.googleKey == user.googleKey
                        && databaseUser.icon == user.icon

                if ((social == TWITTER && twitterCondition)
                    || (social == FACEBOOK && facebookCondition)
                    || (social == GOOGLE && googleCondition)
                ) {
                    isUserPresent = true
                    return@forEach
                }
            }
            if (!isUserPresent) {
                db.collection(USERS_COLLECTION).document().set(user)
                    .addOnSuccessListener {
                        Timber.d("$social info saved")
                    }.addOnFailureListener { e ->
                        Timber.e("Error twitter save: $e")
                    }
            }

            callback?.onLoggedInWithFilledInfo()
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