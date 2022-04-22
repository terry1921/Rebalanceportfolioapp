package com.mx.terryrockstar.rebalancedportfolioapp.utils

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.mx.terryrockstar.rebalancedportfolioapp.App

class Preferences {

    fun setPreference(key: String?, value: Any) {
        try {
            val editor = App.preferences.edit()
            when (value) {
                is String -> editor.putString(key, value)
                is Int -> editor.putInt(key, value)
                is Boolean -> editor.putBoolean(key, value)
                is Long -> editor.putLong(key, value)
                is Float -> editor.putFloat(key, value)
                else -> editor.putString(key, value.toString())
            }
            editor.apply()
        } catch (e: java.lang.Exception) {
            Print.d("Preferences", "setPreference:" + e.message, e.cause)
        }
    }

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

    fun <T> getPreference(key: String?, c: Class<T>): Any? {
        return try {
            when(c.name) {
                String::class.java.name -> {
                    App.preferences.getString(key, null)
                }
                Int::class.java.name -> {
                    App.preferences.getInt(key, -1)
                }
                Boolean::class.java.name -> {
                    App.preferences.getBoolean(key, false)
                }
                Long::class.java.name -> {
                    App.preferences.getLong(key, -1L)
                }
                Float::class.java.name -> {
                    App.preferences.getFloat(key, -1F)
                }
                else -> {
                    App.preferences.getString(key, null)
                }
            }
        } catch (e: Exception) {
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