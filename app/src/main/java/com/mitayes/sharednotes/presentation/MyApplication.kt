package com.mitayes.sharednotes.presentation

import android.app.Application
import android.content.Context
import com.mitayes.sharednotes.data.sqlite.LocalDBSQLite

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        localDbSQLIte = LocalDBSQLite()
    }

    companion object {
        lateinit  var appContext: Context
        lateinit  var localDbSQLIte: LocalDBSQLite
    }
}