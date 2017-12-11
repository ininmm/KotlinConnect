package com.ininmm.kotlinconnect.api

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by User
 * on 2017/12/10.
 */

fun <T> Observable<Array<GlobalJson<T>>>.withAPI(): Observable<GlobalJson<T>> {
    return this.flatMap {
        if (it[0].retCode == 1 || it[0].error?.errorCode == ApiException.VALIDATE_FAILED) {
            return@flatMap Observable.just(it[0])
        } else {
//            return@flatMap Observable.error<GlobalJson<T>>(ApiException(when (it[0].error)))
            return@flatMap Observable.error<GlobalJson<T>>(ApiException(it[0].error!!))
        }
    }
}

fun Retrofit.Builder.buildWithAPI(): Retrofit {
    val builder = OkHttpClient.Builder()
    val token = "123"
    //未來做 checkToken
    if (token != null && token.isEmpty()) {
        builder.addInterceptor { chain: Interceptor.Chain? ->
            val newRequest = chain!!.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
            return@addInterceptor chain.proceed(newRequest)
        }
    } else {
        builder.addInterceptor { chain: Interceptor.Chain? ->
            val newRequest = chain!!.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
            return@addInterceptor chain.proceed(newRequest)
        }
    }
    val client = builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build()
    val baseUrl = "https://www.ridelife.com.tw"
    val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                    .setLenient()
                    .create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    return retrofit
}