package com.mikhailovskii.weatherandroid.di

import com.mikhailovskii.weatherandroid.ui.choose_location.ChooseLocationActivity
import com.mikhailovskii.weatherandroid.ui.choose_location.ChooseLocationContract
import com.mikhailovskii.weatherandroid.ui.choose_location.ChooseLocationPresenter
import com.mikhailovskii.weatherandroid.ui.forecast.ForecastContract
import com.mikhailovskii.weatherandroid.ui.forecast.ForecastFragment
import com.mikhailovskii.weatherandroid.ui.forecast.ForecastPresenter
import com.mikhailovskii.weatherandroid.ui.login.LoginActivity
import com.mikhailovskii.weatherandroid.ui.login.LoginContract
import com.mikhailovskii.weatherandroid.ui.login.LoginPresenter
import com.mikhailovskii.weatherandroid.ui.maps.MapsContract
import com.mikhailovskii.weatherandroid.ui.maps.MapsFragment
import com.mikhailovskii.weatherandroid.ui.maps.MapsPresenter
import com.mikhailovskii.weatherandroid.ui.settings.SettingsContract
import com.mikhailovskii.weatherandroid.ui.settings.SettingsFragment
import com.mikhailovskii.weatherandroid.ui.settings.SettingsPresenter
import com.mikhailovskii.weatherandroid.ui.shop.ShopContract
import com.mikhailovskii.weatherandroid.ui.shop.ShopFragment
import com.mikhailovskii.weatherandroid.ui.shop.ShopPresenter
import com.mikhailovskii.weatherandroid.ui.sticker_purchase.StickerPurchaseActivity
import com.mikhailovskii.weatherandroid.ui.sticker_purchase.StickerPurchaseContract
import com.mikhailovskii.weatherandroid.ui.sticker_purchase.StickerPurchasePresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AppModules {

    val mvpModule = module {

        scope(named<LoginActivity>()) {
            scoped { LoginPresenter() as LoginContract.LoginPresenter }
        }

        scope(named<ChooseLocationActivity>()) {
            scoped { ChooseLocationPresenter() as ChooseLocationContract.ChooseLocationPresenter }
        }

        scope(named<ForecastFragment>()) {
            scoped { ForecastPresenter() as ForecastContract.ForecastPresenter }
        }

        scope(named<MapsFragment>()) {
            scoped { MapsPresenter() as MapsContract.MapsPresenter }
        }

        scope(named<SettingsFragment>()) {
            scoped { SettingsPresenter() as SettingsContract.SettingsPresenter }
        }

        scope(named<ShopFragment>()) {
            scoped { ShopPresenter() as ShopContract.ShopPresenter }
        }

        scope(named<StickerPurchaseActivity>()) {
            scoped { StickerPurchasePresenter() as StickerPurchaseContract.StickerPurchasePresenter }
        }

    }

}