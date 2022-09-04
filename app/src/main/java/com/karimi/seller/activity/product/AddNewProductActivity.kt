package com.karimi.seller.activity.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R
import kotlinx.android.synthetic.main.toolbar_new_p.*

class AddNewProductActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_product)

        initToolbar()
    }


    fun initToolbar(){
        arrayOf(title_new_product,back_new_product).forEach { it.setOnClickListener { onBackPressed() } }
    }
    
}