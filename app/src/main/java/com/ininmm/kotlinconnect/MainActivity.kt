package com.ininmm.kotlinconnect

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ininmm.kotlinconnect.Util.toast
import com.ininmm.kotlinconnect.api.buildWithAPI
import com.ininmm.kotlinconnect.api.model.WeatherModel
import com.ininmm.kotlinconnect.api.service.WeatherAPI
import com.ininmm.kotlinconnect.api.withAPI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    @Test.Companion.Speed
    private var speed: Long = 0
    private val retrofitAPI = Retrofit.Builder().buildWithAPI().create(WeatherAPI::class.java)
    private lateinit var baseBean: BaseBean
    var Array =  ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val basebean = BaseBean.getInstance(this)
//        apiButton.setOnClickListener { apiClickListener }
        apiButton.setOnClickListener {
            Log.i(localClassName, "Click!!")
            apiTest()
        }
        apiButton.text = getString(R.string.sample_text)
        mainTextView.setText(R.string.app_name)
//        basebean.dosomething()
        Array.add("A")
        Array.add("B")
        Array.add("C")
        Log.i(localClassName, convertArrayListToString(Array))
        Log.i(localClassName, convertStringToArrayList(convertArrayListToString(Array)).toString())

    }

    private var apiClickListener: View.OnClickListener = View.OnClickListener {
        Log.i(localClassName, "Click!!")
        apiTest()
    }

    private fun apiTest() {
        val lat = 24.1111
        val lng = 120.64892
        receiveWeather(lat, lng)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    Log.i(localClassName, it?.get(0)?.area)
                    Log.i(localClassName, it?.get(0)?.windSpeed.toString())
                    Log.i(localClassName, it?.get(0)?.humidity.toString())
                    Log.i(localClassName, it?.get(0)?.weather)
                    Log.i(localClassName, it?.get(0)?.temperatureC.toString())
                }, {
                    it.printStackTrace()
                    //create error dialog
                })
    }

    fun receiveWeather(latitude: Double, longitude: Double): Observable<Array<WeatherModel?>?>? {
        return retrofitAPI.getWeather(latitude, longitude).withAPI().map { it.getretVal() }
    }

    fun extensionTest() {
        toast("asdf", Toast.LENGTH_LONG)
    }

    fun RxjavaTest() {
        Observable.intervalRange(0, 5, 2, 1, TimeUnit.SECONDS)
                .doOnSubscribe { Log.i("Reactive1", "doOnSubscribe: " + Thread.currentThread().name) }
                .subscribeOn(Schedulers.io())
                .doOnNext { Log.i("Reactive2", Thread.currentThread().name) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.i("Reactive3", Thread.currentThread().name) })
    }

    fun setSpeed(@Test.Companion.Speed speed: Long) {
        this.speed = speed
    }

    companion object {
        var strSeparator = ", "
        fun convertArrayListToString(arrayList: ArrayList<String>): String {
            var str = ""
            for (i in 0 until arrayList.size) {
                str += arrayList[i]
                // 最後一個不加逗號
                if (i < arrayList.size - 1) {
                    str += strSeparator
                }
            }
            return str
        }

        @JvmStatic fun convertStringToArrayList(str: String): ArrayList<String> {
            val arrayList = ArrayList<String>(str.split(strSeparator))
            print("convertStringToArrayList" + arrayList)
            return arrayList
        }
    }
}
