package com.mx.terryrockstar.rebalancedportfolioapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.multidex.MultiDex

class App : Application() {

    var mContext: Context? = null

    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences(CONFIG_FILE_NAME, MODE_PRIVATE)
        mContext = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        lateinit var preferences: SharedPreferences
        const val CONFIG_FILE_NAME: String = BuildConfig.CONF
    }

}