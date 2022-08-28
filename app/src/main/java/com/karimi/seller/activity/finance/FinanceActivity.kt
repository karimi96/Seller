package com.karimi.seller.activity.finance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R
import kotlinx.android.synthetic.main.activity_customer_view.*

class FinanceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

        chart_bar_price.barChartAdapter()

    }
}