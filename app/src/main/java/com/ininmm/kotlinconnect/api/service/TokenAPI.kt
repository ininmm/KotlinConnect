package com.ininmm.kotlinconnect.api.service

import com.ininmm.kotlinconnect.api.GlobalJson
import com.ininmm.kotlinconnect.api.model.TokenModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by User
 * on 2017/12/16.
 */
interface TokenAPI {
    @GET("auth/apiLogin")
    fun refreshToken(@Query("data") data: String): Observable<Array<TokenModel>>
}
        //data=[{"account":"Webdev01","passwd":"Webdev01"}]