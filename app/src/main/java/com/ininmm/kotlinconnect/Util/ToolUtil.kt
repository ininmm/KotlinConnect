package com.ininmm.kotlinconnect.Util

import android.app.Activity
import android.widget.Toast

/**
 * Created by User
 * on 2017/11/26.
 */
fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
//fun convertArrayListToString(arrayList: ArrayList<String>): String {
//    var str = ""
//    for (i in 0 until arrayList.size) {
//        str = str + arrayList[i]
//        // 最後一個不加逗號
//        if (i < arrayList.size - 1) {
//            str = str + strSeparator
//        }
//    }
//    return str
//}


fun ArrayListToString(arrayList: ArrayList<String>): String {
    var str = ""
    val strSeparator = ", "
    for (i in 0 until arrayList.size) {
        str += arrayList[i]
        if (i < arrayList.size - 1) {
            str += strSeparator
        }
    }
    return str
}