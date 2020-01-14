package com.mikhailovskii.weatherandroid.ui.sticker_purchase

import com.mikhailovskii.weatherandroid.data.entities.StickerPack
import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface StickerPurchaseContract {

    interface StickerPurchaseView : MvpView {

        fun onStickerPackByNameLoaded(stickerPack: StickerPack)

        fun onStickerPackByNameFailed()

    }

    interface StickerPurchasePresenter : MvpPresenter<StickerPurchaseView> {

        fun getStickerPackByName(name: String)

    }

}