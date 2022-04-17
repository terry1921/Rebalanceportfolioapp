package com.mx.terryrockstar.rebalancedportfolioapp.utils

import android.util.Log
import com.mx.terryrockstar.rebalancedportfolioapp.BuildConfig
import org.json.JSONObject

/**
 * Created by Enrique Espinoza on 2019-10-23.
 */
class Print {

    companion object {
        private const val TAG = "Print"

        fun i(message: Any) {
            i(TAG, message)
        }

        fun i(tag: String, message: Any) {
            if (BuildConfig.DEBUG) {
                Log.i(tag, message.toString())
            }
        }

        fun d(message: Any) {
            d(TAG, message)
        }

        fun d(tag: String, message: Any) {
            d(tag, message, null)
        }

        fun d(tag: String, message: Any, json: String){
            d(tag, "$message\n${JSONObject(json).toString(2)}")
        }

        fun d(message: Any, throwable: Throwable){
            d(TAG, message, throwable)
        }

        fun d(tag: String, message: Any, throwable: Throwable?) {
            if (BuildConfig.DEBUG) {
                if (throwable == null) {
                    Log.d(tag, message.toString())
                } else {
                    Log.d(tag, message.toString(), throwable)
                }
            }
        }

        fun v(message: Any) {
            v(TAG, message)
        }

        fun v(tag: String, message: Any) {
            v(tag, message.toString(), null)
        }

        fun v(message: Any, throwable: Throwable) {
            v(TAG, message, throwable)
        }

        fun v(tag: String, message: Any, throwable: Throwable?) {
            if (BuildConfig.DEBUG) {
                if (throwable == null) {
                    Log.v(tag, message.toString())
                } else {
                    Log.v(tag, message.toString(), throwable)
                }
            }
        }

        fun e(message: Any) {
            e(TAG, message)
        }

        fun e(tag: String, message: Any) {
            e(tag, message.toString(), null)
        }

        fun e(message: Any, throwable: Throwable) {
            e(TAG, message, throwable)
        }

        fun e(tag: String, message: Any?, throwable: Throwable?) {
            if (BuildConfig.DEBUG) {
                if (throwable == null) {
                    Log.e(tag, message.toString())
                } else {
                    Log.e(tag, message.toString(), throwable)
                }
            }
        }

        fun w(message: Any) {
            w(TAG, message)
        }

        fun w(tag: String, message: Any) {
            w(tag, message.toString(), null)
        }

        fun w(message: Any, throwable: Throwable) {
            w(TAG, message, throwable)
        }

        fun w(tag: String, message: Any?, throwable: Throwable?) {
            if (BuildConfig.DEBUG) {
                if (throwable == null) {
                    Log.w(tag, message.toString())
                } else {
                    Log.w(tag, message.toString(), throwable)
                }
            }
        }
    }
}