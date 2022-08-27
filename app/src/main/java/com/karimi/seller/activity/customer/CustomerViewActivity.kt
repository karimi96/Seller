package com.karimi.seller.activity.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R
import com.karimi.seller.helper.App
import com.karimi.seller.model.Customers
import kotlinx.android.synthetic.main.include_box_customer_view.*
import kotlinx.android.synthetic.main.include_toolbar_customer_view.*
import java.util.*

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

        initData()


    }


    private fun initData(){
//        tv_id.setText("#${this_customer?.id}")
        tv_customer_name.text = this_customer?.name

        tv_order_count.text = "700 سفارش"
        tv_amount_all_order.text = App.priceFormat(28000000.0,true)
        tv_order_last_date.text = App.getFormattedDate(Date())

        try {
//            tv_date.setText("در تاریخ ${App.getFormattedDate(this_customer?.updated_at)}ویرایش شده \n در تاریخ${App.getFormattedDate(this_customer?.created_at)} ثبت شده")
        }catch (e:Exception){

        }
    }




}