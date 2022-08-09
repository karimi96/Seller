package com.karimi.seller.helper

import android.annotation.SuppressLint
import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        context = this
        MultiDex.install(this)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }



    }