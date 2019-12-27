package com.mikhailovskii.weatherandroid.ui.maps

import com.mikhailovskii.weatherandroid.data.api.MapsAPIFactory
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MapsPresenter : BasePresenter<MapsContract.MapsView>(), MapsContract.MapsPresenter {

    val mapsApi = MapsAPIFactory.getInstance().apiService

    override fun getDataByLocation(lat: Double, lon: Double) {
        compositeDisposable.add(mapsApi.getLocation(lat = lat.toString(), lon = lon.toString())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { view?.showLoadingIndicator(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                println(result?.results?.get(0)?.addressComponents?.get(0)?.shortName)
            }, {
                view?.onLoadingFailed()
            })
        )
    }

}