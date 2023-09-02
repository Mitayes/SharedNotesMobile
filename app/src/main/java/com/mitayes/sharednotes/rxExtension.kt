package com.mitayes.sharednotes

import android.util.Log
import com.mitayes.sharednotes.presentation.MyApplication
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.math.roundToInt

fun Completable.onIo() = this.subscribeOn(Schedulers.io())
fun Completable.onUi() = this.observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.onIo() = this.subscribeOn(Schedulers.io())
fun <T> Single<T>.onUi() = this.observeOn(AndroidSchedulers.mainThread())

fun doIf(predicate: Boolean, action: (Unit) -> Unit) {
    if (predicate) {
        action.invoke(Unit)
    }
}

fun logD(message: String){
    Log.d("CustomLog", message)
}

fun logE(message: String){
    Log.e("CustomLog", message)
}

fun Float.toDp() = this * MyApplication.appContext.resources.displayMetrics.density
fun Int.toDp() = this * MyApplication.appContext.resources.displayMetrics.density.roundToInt()