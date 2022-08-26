package com.karimi.seller.helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.karimi.seller.database.AppDatabase
import com.karimi.seller.helper.Config.JPG
import com.karimi.seller.helper.Config.PATH_IMAGES
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.io.*
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



        fun getByte(uri: Uri): ByteArray{
            val baos = ByteArrayOutputStream()
            val fis: FileInputStream
            try {
                fis = FileInputStream(File(uri.getPath()))
                val buf = ByteArray(1024)
                var n: Int
                while (-1 != fis.read(buf).also { n = it }) baos.write(buf, 0, n)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return baos.toByteArray()
        }

        fun saveFile(byteArray: ByteArray) : String{
            val outStream: FileOutputStream
            try {
                val path = File(Environment.getExternalStorageDirectory(), PATH_IMAGES)
                path.mkdirs()
                val fileName = "image_${System.currentTimeMillis()}$JPG"
                val file = File(path,fileName)
                Log.e("qqqq", "saveFile: ${file.path}" )
                outStream = FileOutputStream(file)
                outStream.write(byteArray)
                outStream.close()
                return file.path
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return ""
        }

    }
}