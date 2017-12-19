package com.ininmm.kotlinconnect

import com.ininmm.kotlinconnect.api.buildWithAPI
import com.ininmm.kotlinconnect.api.checkToken
import com.ininmm.kotlinconnect.api.model.TokenModel
import com.ininmm.kotlinconnect.api.model.WeatherModel
import com.ininmm.kotlinconnect.api.service.TokenAPI
import com.ininmm.kotlinconnect.api.service.WeatherAPI
import com.ininmm.kotlinconnect.api.withAPI
import com.ininmm.kotlinconnect.api.withTokenAPI
import io.reactivex.Observable
import retrofit2.Retrofit

/**
 * Created by User
 * on 2017/12/14.
 */
class MyViewModel {
    private val retrofitAPI = Retrofit.Builder().buildWithAPI().create(WeatherAPI::class.java)
    private val tokenAPI = Retrofit.Builder().buildWithAPI().create(TokenAPI::class.java)


    fun receiveWeather(latitude: Double, longitude: Double): Observable<Array<WeatherModel?>?>? {
//        return retrofitAPI.getWeather(latitude, longitude).withAPI().map { it.getretVal() }
        return retrofitAPI
                .getWeather(latitude, longitude)
                .withAPI()
                .checkToken(KotlinConnectApp.instance, retrofitAPI.getWeather(latitude, longitude).withAPI())
                .map { it.getretVal() }
    }

    fun checkToken(): Observable<String> {
        return tokenAPI.refreshToken("[{\"account\":\"Webdev01\",\"passwd\":\"Webdev01\"}]").withTokenAPI().map { it.getretVal() }
    }
}