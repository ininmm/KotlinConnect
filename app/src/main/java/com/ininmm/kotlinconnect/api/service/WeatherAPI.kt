package com.ininmm.kotlinconnect.api.service

import com.ininmm.kotlinconnect.api.GlobalJson
import com.ininmm.kotlinconnect.api.model.WeatherModel
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by User
 * on 2017/12/10.
 */
interface WeatherAPI {
    @FormUrlEncoded
    @POST("api/weather/get")
    fun getWeather(@Field("lat") latitude: Double,
                   @Field("lng") longitude: Double): Observable<Array<GlobalJson<WeatherModel>>>
}