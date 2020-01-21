package com.mikhailovskii.weatherandroid.ui.forecast


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherElement
import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherResponse
import com.mikhailovskii.weatherandroid.ui.adapter.WeatherAdapter
import com.mikhailovskii.weatherandroid.util.convertKelvinToCelsius
import com.mikhailovskii.weatherandroid.util.getWindDirection
import com.mikhailovskii.weatherandroid.util.showErrorToast
import kotlinx.android.synthetic.main.fragment_forecast.*
import java.util.*

class ForecastFragment : Fragment(), ForecastContract.ForecastView {

    private val presenter = ForecastPresenter()
    private var adapter: WeatherAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)

        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.forecastStatusBar)

        weather_list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = WeatherAdapter()
        weather_list.adapter = adapter

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

        presenter.getCityForecast()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun onCurrentCityWeatherLoaded(response: WeatherResponse?) {
        val temperature = convertKelvinToCelsius(response?.weatherTemp?.temp ?: 0.0).toInt()
        val feelsLike = convertKelvinToCelsius(response?.weatherTemp?.feelsLike ?: 0.0).toInt()

        weather_description_tv.text = response?.overcast?.get(0)?.mainInfo

        temperature_tv.text = resources.getString(R.string.temperature_in_celsius, temperature)

        humidity_value_tv.text =
            resources.getString(R.string.humidity_percents, response?.weatherTemp?.humidity)

        pressure_value_tv.text = "${response?.weatherTemp?.pressure}"

        feels_like_value_tv.text = resources.getString(R.string.temperature_in_celsius, feelsLike)

        wind_value_tv.text = resources.getString(
            R.string.wind_in_kph,
            getWindDirection(response?.wind?.degree ?: 0),
            response?.wind?.speed!!.toInt()
        )

        if (response.overcast?.get(0)?.icon!!.contains("02", ignoreCase = true)
            || response.overcast?.get(0)?.icon!!.contains("03", ignoreCase = true)
            || response.overcast?.get(0)?.icon!!.contains("04", ignoreCase = true)
        ) {
            weather_iv.setImageResource(R.drawable.few_clouds)
        } else if (response.overcast?.get(0)?.icon!!.contains("01", ignoreCase = true)
            || response.overcast?.get(0)?.icon!!.contains("13", ignoreCase = true)
        ) {
            weather_iv.setImageResource(R.drawable.snow)
        } else if (response.overcast?.get(0)?.icon!!.contains("09", ignoreCase = true)) {
            weather_iv.setImageResource(R.drawable.shower_rain)
        } else if (response.overcast?.get(0)?.icon!!.contains("10", ignoreCase = true)) {
            weather_iv.setImageResource(R.drawable.rain)
        } else if (response.overcast?.get(0)?.icon!!.contains("11", ignoreCase = true)) {
            weather_iv.setImageResource(R.drawable.thunderstorm)
        } else if (response.overcast?.get(0)?.icon!!.contains("50", ignoreCase = true)) {
            weather_iv.setImageResource(R.drawable.mist)
        }

    }

    override fun onCurrentCityWeatherFailed() {

    }

    override fun onCityFromPreferencesLoaded(response: String?) {
        city_tv.text = resources.getString(R.string.location_with_emoji, response)
    }

    override fun onCityFromPreferencesFailed() {

    }

    override fun onWeatherForecastLoaded(weatherList: List<WeatherElement>) {
        adapter?.setData(weatherList)

        val entries = ArrayList<Entry>()

        for (i in weatherList.indices) {
            entries.add(Entry(i.toFloat(), weatherList[i].temp?.toFloat() ?: 0f))
        }

        val lineDataSet = LineDataSet(entries, "")
        lineDataSet.color = Color.RED
        lineDataSet.setDrawValues(false)

        line_chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        line_chart.xAxis.valueFormatter = XAxisFormatter(weatherList)
        line_chart.xAxis.axisLineColor = Color.WHITE
        line_chart.xAxis.textColor = Color.WHITE

        line_chart.axisLeft.valueFormatter = YAxisFormatter()
        line_chart.axisLeft.textColor = Color.WHITE
        line_chart.axisLeft.zeroLineColor = Color.WHITE
        line_chart.axisLeft.axisLineColor = Color.WHITE

        line_chart.setGridBackgroundColor(Color.WHITE)

        val data = LineData(lineDataSet)

        line_chart.data = data
        line_chart.legend.isEnabled = false
        line_chart.description.isEnabled = false
        line_chart.axisRight.isEnabled = false
        line_chart.invalidate()
    }

    override fun onWeatherForecastFailed() {
        showErrorToast(getString(R.string.loading_failed))
    }

}