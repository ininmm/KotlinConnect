package com.ininmm.kotlinconnect.api.model

import android.support.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
/**
 * Created by User
 * on 2017/12/16.
 */
class TokenModel {
    @Expose
    var retCode: Int = 0

    @Expose
    var retVal: String? = null
    fun getretVal(): String? {
        return retVal
    }
    @Expose
    var error: Error? = null

    fun setretVal(retVal: String?): TokenModel {
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
}