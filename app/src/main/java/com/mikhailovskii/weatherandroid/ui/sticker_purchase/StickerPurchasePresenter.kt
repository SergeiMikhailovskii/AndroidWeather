package com.mikhailovskii.weatherandroid.ui.sticker_purchase

import com.google.firebase.firestore.FirebaseFirestore
import com.mikhailovskii.weatherandroid.data.entities.StickerPack
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter

class StickerPurchasePresenter : BasePresenter<StickerPurchaseContract.StickerPurchaseView>(),
    StickerPurchaseContract.StickerPurchasePresenter {

    private val db = FirebaseFirestore.getInstance()

    override fun getStickerPackByName(name: String) {
        db.collection("stickers")
            .whereEqualTo("title", name)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    for (document in result) {
                        val stickerPack = document.toObject(StickerPack::class.java)
                        view?.onStickerPackByNameLoaded(stickerPack)
                    }
                } else {
                    view?.onStickerPackByNameFailed()
                }
            }
            .addOnFailureListener {
                view?.onStickerPackByNameFailed()
            }
    }

}