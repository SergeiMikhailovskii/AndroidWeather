package com.mikhailovskii.weatherandroid.ui.shop

import com.mikhailovskii.weatherandroid.data.entities.StickerPack
import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface ShopContract {

    interface ShopView : MvpView {

        fun onStickerListLoaded(stickers: List<StickerPack>)

        fun onStickerListFailed()

    }

    interface ShopPresenter : MvpPresenter<ShopView> {

        fun getStickerList()

    }


}