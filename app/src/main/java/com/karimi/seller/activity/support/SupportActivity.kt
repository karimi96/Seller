package com.karimi.seller.activity.support

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R
import kotlinx.android.synthetic.main.activity_support.*
import kotlinx.android.synthetic.main.toolbar_support.*

class SupportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        arrayOf(title_support, back_support).forEach { it.setOnClickListener { onBackPressed() } }

        tel.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("tel:${getString(R.string.tel_action)}")
            startActivity(i)
        }

    }
}