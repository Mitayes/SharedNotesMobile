package com.mitayes.sharednotes

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun Completable.onIo() = this.subscribeOn(Schedulers.io())
fun Completable.onUi() = this.observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.onIo() = this.subscribeOn(Schedulers.io())
fun <T> Single<T>.onUi() = this.observeOn(AndroidSchedulers.mainThread())
