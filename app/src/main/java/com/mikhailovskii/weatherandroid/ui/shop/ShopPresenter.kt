package com.mikhailovskii.weatherandroid.ui.shop

import com.mikhailovskii.weatherandroid.data.entities.StickerPack
import com.mikhailovskii.weatherandroid.data.firebase.FirebaseDataCallback
import com.mikhailovskii.weatherandroid.data.firebase.FirebaseModel
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter

class ShopPresenter : BasePresenter<ShopContract.ShopView>(), ShopContract.ShopPresenter {

    override fun getStickerList() {
        FirebaseModel().getStickersCollectionList(object :
            FirebaseDataCallback<ArrayList<StickerPack>> {

            override fun onFirebaseDataLoaded(data: ArrayList<StickerPack>) {
                if (data.isNotEmpty()) {
                    view?.onStickerListLoaded(data)
                } else {
                    view?.onStickerListFailed()
                }
            }

            override fun onFirebaseDataFailed() {
                view?.onStickerListFailed()
            }

        })
    }

}