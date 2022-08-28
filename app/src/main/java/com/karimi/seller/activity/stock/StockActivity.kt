package com.karimi.seller.activity.stock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R
import com.karimi.seller.adapter.ProductListAdapter_2
import com.karimi.seller.adapter.TagInfoAdapter

class StockActivity : AppCompatActivity() {

    private var adapter : ProductListAdapter_2? =null
    private var adapterTag : TagInfoAdapter? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        
    }
}