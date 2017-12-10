package com.ininmm.kotlinconnect

import android.content.Context

/**
 * Created by User
 * on 2017/11/26.
 */
class BaseBean private constructor() {
//    fun getAppName(): String = context.getString(R.string.app_name)
    companion object {
        private var instance: BaseBean?= null
        fun getInstance(context: Context): BaseBean {
            if (instance == null) {
                instance = BaseBean()
            }
            return instance!!
        }

    }
    fun dosomething() {

    }
}