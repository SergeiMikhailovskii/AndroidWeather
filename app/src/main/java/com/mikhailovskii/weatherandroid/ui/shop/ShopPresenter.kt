package com.mikhailovskii.weatherandroid.ui.shop

import com.mikhailovskii.weatherandroid.data.entities.StickerElement
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter

class ShopPresenter : BasePresenter<ShopContract.ShopView>(), ShopContract.ShopPresenter {

    override fun getStickerList() {
        val stickers = arrayListOf(
            StickerElement(
                icon = "https://avatars2.githubusercontent.com/u/33265881?s=460&v=4",
                title = "Title1",
                price = "0,99"
            ),
            StickerElement(
                icon = "https://avatars2.githubusercontent.com/u/33265881?s=460&v=4",
                title = "Title2",
                price = "0,99"
            ),
            StickerElement(
                icon = "https://avatars2.githubusercontent.com/u/33265881?s=460&v=4",
                title = "Title3",
                price = "0,99"
            ),
            StickerElement(
                icon = "https://avatars2.githubusercontent.com/u/33265881?s=460&v=4",
                title = "Title4",
                price = "0,99"
            ),
            StickerElement(
                icon = "https://avatars2.githubusercontent.com/u/33265881?s=460&v=4",
                title = "Title5",
                price = "0,99"
            )
        )

        view?.onStickerListLoaded(stickers)
    }

}