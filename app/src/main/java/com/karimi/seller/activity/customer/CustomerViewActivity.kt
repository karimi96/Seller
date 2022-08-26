package com.karimi.seller.activity.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R
import com.karimi.seller.helper.App
import com.karimi.seller.model.Customers

class CustomerViewActivity : AppCompatActivity() {

    var customer_id : Int? = null
    var position : Int? = null

    private var this_customer : Customers = Customers()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_view)

        if (intent.extras != null){
            customer_id = intent.getIntExtra("product_id",-1)
            position = intent.getIntExtra("pos",-1)
        }


        this_customer = App.database.getAppDao().selectCustomerById(customer_id!!)!!








    }
}