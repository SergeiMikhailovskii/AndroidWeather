package com.mikhailovskii.weatherandroid.ui.maps

import com.mikhailovskii.weatherandroid.data.api.MapsAPIFactory
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MapsPresenter : BasePresenter<MapsContract.MapsView>(), MapsContract.MapsPresenter {

    val mapsApi = MapsAPIFactory.getInstance().apiService

    override fun getDataByLocation(lat: Double, lon: Double) {
        compositeDisposable.add(mapsApi.getLocation("$lat,$lon")
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { view?.showLoadingIndicator(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                val data = "\uD83D\uDCCD ${result?.results?.get(0)?.addressComponents?.get(2)?.shortName}, " +
                        "${result?.results?.get(0)?.addressComponents?.get(5)?.longName}"

                view?.onDataLoaded(data)

            }, {
                view?.onLoadingFailed()
            })
        )
    }

}