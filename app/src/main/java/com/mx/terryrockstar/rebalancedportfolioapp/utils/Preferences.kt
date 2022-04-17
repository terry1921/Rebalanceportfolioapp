package com.mx.terryrockstar.rebalancedportfolioapp.utils

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.mx.terryrockstar.rebalancedportfolioapp.App

class Preferences {

    fun <T> setPreferenceObject(key: String?, y: T) {
        val editor: SharedPreferences.Editor = App.preferences.edit()
        try {
            val gson = Gson()
            val inString = gson.toJson(y)
            editor.putString(key, inString)
            editor.apply()
        } catch (t: Throwable) {
            Print.e("Preferences", "setPreferenceObject:" + t.message, t.cause)
        }
    }

    fun <T> getPreferenceObject(key: String?, c: Class<T>?): T? {
        return try {
            val gson = Gson()
            val value: String? = App.preferences.getString(key, "")
            gson.fromJson(value, c)
        } catch (t: Throwable) {
            Print.e("Preferences", "getPreferenceObject: " + t.message, t.cause)
            null
        }
    }

    fun deletePreferences(vararg keys: String?) {
        try {
            val editor = App.preferences.edit()
            keys.forEach { editor.remove(it) }
            editor.apply()
        } catch (e: Exception) {
            Print.d("Preferences", "deletePreferences: " + e.message, e.cause)
        }
    }

}