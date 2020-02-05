package com.mikhailovskii.weatherandroid.ui.sticker_purchase

import com.mikhailovskii.weatherandroid.data.entities.StickerPack
import com.mikhailovskii.weatherandroid.data.firebase.FirebaseDataCallback
import com.mikhailovskii.weatherandroid.data.firebase.FirebaseModel
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter

class StickerPurchasePresenter : BasePresenter<StickerPurchaseContract.StickerPurchaseView>(),
    StickerPurchaseContract.StickerPurchasePresenter {

    override fun getStickerPackByName(name: String) {

        FirebaseModel().getStickerPackByName(name, object : FirebaseDataCallback<StickerPack> {

            override fun onFirebaseDataLoaded(data: StickerPack) {
                view?.onStickerPackByNameLoaded(data)
            }

            override fun onFirebaseDataFailed() {
                view?.onStickerPackByNameFailed()
            }

        })
    }

}