package com.karimi.seller.activity.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R
import com.karimi.seller.adapter.CustomerListAdapter
import com.karimi.seller.adapter.TagInfoAdapter

class CustomerActivity : AppCompatActivity() {

    private var adapter : CustomerListAdapter? =null
    private var adapterTag : TagInfoAdapter? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)
    }
}