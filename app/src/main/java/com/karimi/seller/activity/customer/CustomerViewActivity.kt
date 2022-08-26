package com.karimi.seller.activity.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R

class CustomerViewActivity : AppCompatActivity() {

    var customer_id : Int? = null
    var position : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_view)

        if (intent.extras != null){
            customer_id = intent.getIntExtra("product_id",-1)
            position = intent.getIntExtra("pos",-1)
        }
    }
}