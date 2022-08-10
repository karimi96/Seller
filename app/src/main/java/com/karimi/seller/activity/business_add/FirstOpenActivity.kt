package com.karimi.seller.activity.business_add

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R
import kotlinx.android.synthetic.main.box_first_activity.*

class FirstOpenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_open)

        var typeface = Typeface.createFromAsset(assets,"font/iran_sans.ttf")
        description.typeface = typeface
    }
}