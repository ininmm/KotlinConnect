package com.ininmm.kotlinconnect.api

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.ininmm.kotlinconnect.api.model.TokenModel
import com.ininmm.kotlinconnect.api.service.TokenAPI
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
fun <T> Observable<GlobalJson<T>>.checkToken(context: Context,
                                                    request: Observable<GlobalJson<T>>):
        Observable<GlobalJson<T>> {
    return this.flatMap {
        when (it.error?.errorCode) {
            ApiException.TOKEN_EXPIRED -> {
                //Token過期 , 更新Token
                Log.w("app", "Token過期 , 更新Token")
                Retrofit.Builder().buildWithAPI().create(TokenAPI::class.java)
                        .refreshToken("[{\"account\":\"Webdev01\",\"passwd\":\"Webdev01\"}]")
                        .withTokenAPI()
                        .map { it.getretVal() }
                        .flatMap { tokenResponse ->
                            //儲存新Token並創建新請求
//                            val realm = Realm.getDefaultInstance()
//                            val model = realm.where(AccessToken::class.java).findFirst()
//                            realm.beginTransaction()
//                            model.accessToken = tokenResponse.token!!
//                            realm.copyToRealmOrUpdate(model)
//                            realm.commitTransaction()
                            return@flatMap request
                        }
            }
//            ApiException.DUPLICATE_lOGIN -> {
////                context.sendBroadcast(Intent().setAction(GiantGEVApp.DUPLICATE_lOGIN))
//                return@flatMap io.reactivex.Observable.error<GlobalJson<T>>(ApiException(respsonse[0].error!!))
//            }
            else -> {
                //無異常,Token正確
//                return@flatMap io.reactivex.Observable.just(it)
                Log.w("app", "Token過期 , 更新Token")
                Retrofit.Builder().buildWithAPI().create(TokenAPI::class.java)
                        .refreshToken("[{\"account\":\"Webdev01\",\"passwd\":\"Webdev01\"}]")
                        .withTokenAPI()
                        .map { it.getretVal() }
                        .flatMap { tokenResponse ->
                            //儲存新Token並創建新請求
//                            val realm = Realm.getDefaultInstance()
//                            val model = realm.where(AccessToken::class.java).findFirst()
//                            realm.beginTransaction()
//                            model.accessToken = tokenResponse.token!!
//                            realm.copyToRealmOrUpdate(model)
//                            realm.commitTransaction()
                            Log.w("app", tokenResponse)
                            return@flatMap request
                        }
            }
        }
    }
}
//fun <T> Observable<GlobalJson<T>>.checkToken(context: Context, request: () -> Observable<GlobalJson<T>>): Observable<GlobalJson<T>> {
//    return this.flatMap { respsonse ->
//        when (respsonse.error?.errCode) {
//            ApiException.TOKEN_EXPIRED -> {
//                //Token過期 , 更新Token
//                Log.w("app", "Token過期 , 更新Token")
//                Retrofit.Builder().buildWithAPI().create(TokenAPI::class.java)
//                        .refreshToken()
//                        .withAPI()
//                        .map { it.getRetVal()!! }
//                        .flatMap { tokenResponse ->
//                            //儲存新Token並創建新請求
//                            val realm = Realm.getDefaultInstance()
//                            val model = realm.where(AccessToken::class.java).findFirst()
//                            realm.beginTransaction()
//                            model.accessToken = tokenResponse.token!!
//                            realm.copyToRealmOrUpdate(model)
//                            realm.commitTransaction()
//                            return@flatMap request()
//                        }
//            }
//            ApiException.DUPLICATE_lOGIN -> {
//                context.sendBroadcast(Intent().setAction(GiantGEVApp.DUPLICATE_lOGIN))
//                return@flatMap Observable.error<GlobalJson<T>>(ApiException(respsonse.error!!))
//            }
//            else -> {
//                //無異常,Token正確
//                return@flatMap Observable.just(respsonse)
//            }
//        }
//    }
//}
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

fun Observable<Array<TokenModel>>.withTokenAPI(): Observable<TokenModel> {
    return this.flatMap {
        return@flatMap io.reactivex.Observable.just(it[0])
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