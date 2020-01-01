package com.mikhailovskii.weatherandroid.ui.forecast


import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherResponse
import com.mikhailovskii.weatherandroid.util.getWindDirection
import kotlinx.android.synthetic.main.fragment_forecast.*
import java.util.*

class ForecastFragment : Fragment(), ForecastContract.ForecastView {

    private val presenter = ForecastPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.attachView(this)
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(0xff0f7d71.toInt(), 0xff0e725b.toInt())
        )

        scrollView.background = gradientDrawable

        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val date = "${calendar.getDisplayName(
            Calendar.DAY_OF_WEEK,
            Calendar.LONG, Locale.getDefault()
        )}," +
                " ${calendar.get(Calendar.DATE)}." +
                "${calendar.get(Calendar.MONTH) + 1}." +
                "${calendar.get(Calendar.YEAR)}"

        date_tv.text = date

        presenter.getCityFromPreferences()

        presenter.getCurrentCityWeather()
    }

    @SuppressLint("SetTextI18n")
    override fun onCurrentCityWeatherLoaded(response: WeatherResponse?) {
        weather_description_tv.text = response?.overcast?.get(0)?.mainInfo
        temperature_tv.text = "${response?.weatherTemp?.temp?.minus(273)?.toInt()} ˚C"
        humidity_value_tv.text = "${response?.weatherTemp?.humidity} %"
        precipitation_value_tv.text = "${response?.weatherTemp?.pressure}"
        feels_like_value_tv.text = "${response?.weatherTemp?.feelsLike?.minus(273)?.toInt()} ˚C"
        wind_value_tv.text =
            "${getWindDirection(response?.wind?.degree!!)} ${response.wind?.speed!!} kph"

        if (response.overcast?.get(0)?.icon!!.contains("02", ignoreCase = true)
            || response.overcast?.get(0)?.icon!!.contains("03", ignoreCase = true)
            || response.overcast?.get(0)?.icon!!.contains("04", ignoreCase = true)
        ) {
            weather_iv.setImageResource(R.drawable.few_clouds)
        }

    }

    override fun onCurrentCityWeatherFailed() {

    }

    @SuppressLint("SetTextI18n")
    override fun onCityFromPreferencesLoaded(response: String?) {
        city_tv.text = "\uD83D\uDCCD $response"
    }

    override fun onCityFromPreferencesFailed() {

    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }

}
