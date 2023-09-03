package com.mitayes.sharednotes.presentation

import android.app.Application
import android.content.Context
import com.mitayes.sharednotes.domain.sqlite.LocalDBSQLite
import io.reactivex.disposables.CompositeDisposable

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        localDbSQLIte = LocalDBSQLite()
    }

    companion object {
        lateinit  var appContext: Context
        lateinit  var localDbSQLIte: LocalDBSQLite
        val bag = CompositeDisposable()
    }
}