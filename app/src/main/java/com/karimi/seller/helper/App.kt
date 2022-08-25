package com.karimi.seller.helper

import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.karimi.seller.database.AppDatabase
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.text.DecimalFormat
import java.util.*

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
        fun priceFormat(double: Double, showMoneyType: Boolean):String{
            return if (showMoneyType) "${priceFormat(double)} ${Session.getInstance().moneyType}"
            else priceFormat(double)
        }


        fun getFormattedDate(dateTime: Long?): String? {
            return PersianDateFormat("Y/m/d").format(PersianDate(dateTime))
        }

        fun getFormattedDate(date: Date?): String? {
            return PersianDateFormat("Y/m/d").format(PersianDate(date))
        }


    }
}