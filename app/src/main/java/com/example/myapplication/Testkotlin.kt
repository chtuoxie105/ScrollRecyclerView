package com.example.myapplication

import android.util.Log

class Testkotlin {

    companion object {
        fun test(data: Any) {
            when (data) {
                is String, is Int, is Float -> Log.e("11", "组合.>>:")
                is Float -> {
                    Log.e("11", "Float>>>>")
                }
                else -> {
                    Log.e("11", "未知>>>")
                }
            }
        }
    }

}