package com.ininmm.kotlinconnect.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by User
 * on 2017/12/10.
 */
class WeatherModel {
    /**
     * area : taichung
     * windspeed : 17.7
     * humidity : 72
     * weather : 34
     * tempC : 18.89
     */

    @Expose
    var area: String? = null
    @SerializedName("windspeed")
    @Expose
    var windSpeed: Double = 0.toDouble()
    @Expose
    var humidity: Int = 0
    @Expose
    var weather: String? = null
    @SerializedName("tempC")
    @Expose
    var temperatureC: Double = 0.toDouble()
}