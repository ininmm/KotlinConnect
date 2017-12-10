package com.ininmm.kotlinconnect.api

import android.support.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by User
 * on 2017/12/10.
 */
@Keep
class GlobalJson<T> {
    /**
     * [
     *    {
     *        "retCode": 1,
     *        "retMsg": "get success",
     *        "retVal": [
     *            {
     *                "area": "taichung",
     *                "windspeed": 6.44,
     *                "humidity": 94,
     *                "weather": "29",
     *                "tempC": 12.22
     *            }
     *         ]
     *    }
     * ]
     */

    @Expose
    var retCode: Int = 0

    @Expose
    var retVal: Array<T?>? = null
    fun getretVal(): Array<T?>? {
        return retVal
    }
    @Expose
    var error: Error? = null

    fun setretVal(retVal: Array<T?>?): GlobalJson<T> {
        this.retVal = retVal
        return this
    }
    //    var retVal: List<asdf.RetValBean>? = null
    @Keep
    class Error{
        @SerializedName("errCode")
        @Expose
        var errorCode: String? = null

        @SerializedName("retMsg")
        @Expose
        var message: String? = null

        @SerializedName("invalidFields")
        @Expose
        var fields: Array<String>? = null
    }

    class RetValBean {
        /**
         * area : taichung
         * windspeed : 17.7
         * humidity : 72
         * weather : 34
         * tempC : 18.89
         */

        var area: String? = null
        @SerializedName("windspeed")
        var windSpeed: Double = 0.toDouble()
        var humidity: Int = 0
        var weather: String? = null
        @SerializedName("tempC")
        var temperatureC: Double = 0.toDouble()
    }
}