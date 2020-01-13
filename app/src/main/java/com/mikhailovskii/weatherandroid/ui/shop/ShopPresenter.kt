package com.mikhailovskii.weatherandroid.ui.shop

import com.google.firebase.database.*
import com.mikhailovskii.weatherandroid.data.entities.StickerPack
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import timber.log.Timber

class ShopPresenter : BasePresenter<ShopContract.ShopView>(), ShopContract.ShopPresenter {

    private var database = FirebaseDatabase.getInstance().reference
    private lateinit var query: Query

    override fun getStickerList() {
        val stickers = ArrayList<StickerPack>()

        query = database.child("stickers")
        query.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Timber.e(error.toException())
                view?.onStickerListFailed()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val stickersFirebase = snapshot.children

                    stickersFirebase.forEach { data ->
                        val sticker = data.getValue(StickerPack::class.java)
                        stickers.add(sticker!!)
                    }

                    view?.onStickerListLoaded(stickers)
                }
            }
        })

    }

}