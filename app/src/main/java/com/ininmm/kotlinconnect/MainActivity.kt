package com.ininmm.kotlinconnect

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @Test.Companion.Speed
    private var speed: Long = 0
    private lateinit var baseBean: BaseBean
    private var myViewModel: MyViewModel? = null
    var Array =  ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myViewModel = MyViewModel()
        apiButton.setOnClickListener {
            Log.i(localClassName, "Click!!")
            apiTest()
        }
        apiButton.text = getString(R.string.sample_text)
        mainTextView.setText(R.string.app_name)
        Array.add("A")
        Array.add("B")
        Array.add("C")
        Log.i(localClassName, convertArrayListToString(Array))
        Log.i(localClassName, convertStringToArrayList(convertArrayListToString(Array)).toString())

        var mDisposable: Disposable? = null
        Observable.create<Int> {

            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            Log.e("testNever", "ProgressBar Close1")
            it.onComplete()
            Log.e("testNever", "ProgressBar Close2")
        }
                .flatMap {
                    Log.e("testNever", "start flatmap")
                    if (it == 4) {
                        return@flatMap Observable.just(4)
                    } else {
//                        return@flatMap Observable.never<Int>()
                        return@flatMap Observable.just(it)
                    }
                }
                .doOnTerminate {
                    Log.e("testNever", "start doOnTerminate")
                }
//                .subscribe( object : DisposableObserver<Int>() {
//
//                    override fun onStart() {
//                        super.onStart()
//                        mDisposable = this
//                    }
//
//                    override fun onNext(t: Int) {
//                        Log.e("testNever", "subscribe onNext before dispose $t")
////                        dispose()
//                        Log.e("testNever", "subscribe onNext after dispose $t")
//                    }
//
//                    override fun onError(e: Throwable) {
//                    }
//
//                    override fun onComplete() {
//                        if (mDisposable != null) {
//                            Log.e("testNever", "isDispose ${mDisposable!!.isDisposed}")
//                        }
//                    }
//                })
                .subscribe({
                    Log.e("testNever", "subscribe onNext $it")
                }, { throwable: Throwable? ->
                    Log.e("testNever", "subscribe onError")

                }, {
                    Log.e("testNever", "subscribe onComplete")

                }, { disposable: Disposable? ->
                    mDisposable = disposable
                    Log.e("testNever", "subscribe onSubscribe")
                })
    }

    private fun test(): DisposableObserver<Int> {
        return object : DisposableObserver<Int>() {
            override fun onComplete() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onNext(t: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(e: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    private fun apiTest() {
        val lat = 24.1111
        val lng = 120.64892
        myViewModel?.receiveWeather(lat, lng)
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
