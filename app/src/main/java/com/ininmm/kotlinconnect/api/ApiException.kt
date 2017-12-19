package com.ininmm.kotlinconnect.api

/**
 * Created by User
 * on 2017/12/10.
 */
class ApiException(val error: GlobalJson.Error) : RuntimeException(error.message) {
    /**
     * API錯誤代碼
     * @return 錯誤代碼
     */
    var errorCode: String? = null
        private set

    init {
        this.errorCode = error.errorCode
    }

    companion object {
        var VALIDATE_FAILED = "E000"
        var UNKNOWN_ERROR = "E999"
        var TOKEN_ERROR = "gf0001001"
        var TOKEN_EXPIRED = "123"
        var DUPLICATE_lOGIN = "456"

        // error message 以這個為主
        fun handleErrorMsg(error: Throwable): String {
            val errorMsg = StringBuilder()
            if (error is ApiException) {
                showMessage(error, errorMsg)
            } else {
                errorMsg.append("Unknown Error!")
            }
            return errorMsg.toString()
        }

        // 用errorCode來辦別顯示哪種訊息
        private fun showMessage(error: ApiException, errorMsg: StringBuilder) {
            try {
                when (error.errorCode) {
                    "E000" -> {
                        errorMsg.append("Validate Failed\n")
                        error.error.fields?.forEach { errorMsg.append(it + "\n") }
                    }
                    else -> {
                        errorMsg.append(error.errorCode + "\n" + error.error.message + "\n")
                    }
                }
            } catch (e: Exception) {
                errorMsg.append("Error Unknown Fail")
                e.printStackTrace()
            }
        }
    }
}