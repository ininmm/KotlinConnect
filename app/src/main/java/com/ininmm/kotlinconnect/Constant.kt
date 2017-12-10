package com.ininmm.kotlinconnect

import android.support.annotation.IntDef
import android.support.annotation.StringDef

/**
 * Created by User
 * on 2017/11/26.
 */

class Constant {
    companion object {
        const val API_BASE_URL = "http://api.zhuishushenqi.com"
        const val IMG_BASE_URL = "http://statics.zhuishushenqi.com"
        @Retention(AnnotationRetention.SOURCE)
        @StringDef(Constant.API_BASE_URL, Constant.IMG_BASE_URL)
        annotation class ConnectStatus
    }
}

class Test {
    companion object {
        @IntDef(SLOW, NORMAL, FAST)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Speed

        const val SLOW = 0L
        const val NORMAL = 1L
        const val FAST = 2L
    }
}
