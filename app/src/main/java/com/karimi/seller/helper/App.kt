package com.karimi.seller.helper

import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.karimi.seller.database.AppDatabase
import java.text.DecimalFormat

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        context = this
        database = AppDatabase.getInstance(this)
        MultiDex.install(this)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var database: AppDatabase


        fun branch(): Int {
            return Session.getInstance().branch
        }


        fun toast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }


        fun getString(editText: EditText): String {
            return editText.text.toString().trim()
        }


        fun priceFormat(double: Double):String{
            val decimalFormat = DecimalFormat("###,###,###")
            return decimalFormat.format(double)
        }



    }
}