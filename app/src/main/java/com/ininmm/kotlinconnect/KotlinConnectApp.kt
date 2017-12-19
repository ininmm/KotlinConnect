package com.ininmm.kotlinconnect

import android.app.Application
import io.realm.Realm

/**
 * Created by User
 * on 2017/11/4.
 * Application class
 */

class KotlinConnectApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        Realm.init(this)
    }

    companion object {
        lateinit var instance: KotlinConnectApp
            private set
    }
}
