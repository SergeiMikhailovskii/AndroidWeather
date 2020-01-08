package com.mikhailovskii.weatherandroid.ui.shop

import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface ShopContract {

    interface ShopView : MvpView {

    }

    interface ShopPresenter : MvpPresenter<ShopView> {

    }


}