package com.mikhailovskii.weatherandroid.ui.shop

import com.google.firebase.firestore.FirebaseFirestore
import com.mikhailovskii.weatherandroid.data.entities.StickerPack
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter

class ShopPresenter : BasePresenter<ShopContract.ShopView>(), ShopContract.ShopPresenter {

    private val db = FirebaseFirestore.getInstance()

    override fun getStickerList() {
        db.collection("stickers").get().addOnSuccessListener { result ->
            val stickerPackList = ArrayList<StickerPack>()

            for (document in result) {
                val stickerPack = document.toObject(StickerPack::class.java)
                stickerPackList.add(stickerPack)
            }

            view?.onStickerListLoaded(stickerPackList)
        }
    }

}