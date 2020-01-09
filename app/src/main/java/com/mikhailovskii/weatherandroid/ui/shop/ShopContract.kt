package com.mikhailovskii.weatherandroid.ui.shop

import com.mikhailovskii.weatherandroid.data.entities.StickerElement
import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface ShopContract {

    interface ShopView : MvpView {

        fun onStickerListLoaded(stickers: List<StickerElement>)

        fun onStickerListFailed()

    }

    interface ShopPresenter : MvpPresenter<ShopView> {

        fun getStickerList()

    }


}