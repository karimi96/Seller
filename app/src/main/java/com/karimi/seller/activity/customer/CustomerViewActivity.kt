package com.karimi.seller.activity.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R
import com.karimi.seller.adapter.OrderListHorizontalAdapter
import com.karimi.seller.adapter.TagAdapter
import com.karimi.seller.dialog.InsertCustomerDialog
import com.karimi.seller.helper.App
import com.karimi.seller.model.Customers
import com.karimi.seller.model.Orders
import com.karimi.seller.model.TagList
import kotlinx.android.synthetic.main.activity_customer_view.*
import kotlinx.android.synthetic.main.include_box_customer_view.*
import kotlinx.android.synthetic.main.include_last_order_customer_view.*
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
        initOnClick()
        initAdapterTagList()
        initRecyclerViewOrder()



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


    private fun initOnClick(){
//        ic_close.setOnClickListener {
//            dismiss()
//        }

        edit_customer_view.setOnClickListener {
//            first way
//            listener?.onEditCustomer(this,this_customer, position)

//            InsertCustomerDialog().setValue(this_customer,-1)

//            third way
            InsertCustomerDialog(this, this_customer, -1, null)
                .show(supportFragmentManager, "customer")
        }
    }


    private fun initAdapterTagList(){
        val array_tag = ArrayList<TagList>()
        array_tag.add(TagList("۸۵۰,۰۰۰ تومان بدهکار"))
        array_tag.add(TagList("۲۰,۰۰۰ تومان سود سفارشات"))
        array_tag.add(TagList("۱۰,۰۰۰ تومان تخفیف گرفته"))
        array_tag.add(TagList("میانگین سفارش ۲۸۰,۰۰۰ تومان"))
        array_tag.add(TagList("۸۵۰,۰۰۰ تومان خرید نقدی"))
        array_tag.add(TagList("۸۵۰,۰۰۰ تومان خرید کارتخوان"))
        array_tag.add(TagList(" بانک سامان ۱۹۰,۰۰۰ تومان"))
        array_tag.add(TagList("بانک کشاورزی ۸۵,۰۰۰ تومان"))
        array_tag.add(TagList("بانک ملت ۸۲,۰۰۰ تومان"))

        recycler_tag_customer_view.adapter = TagAdapter(this, array_tag, null)
    }


    private fun initRecyclerViewOrder(){
        recyclerView_last_order.adapter = OrderListHorizontalAdapter(this, ArrayList(App.database.getAppDao().selectOrders(App.branch())),
            object : OrderListHorizontalAdapter.Listener{
                override fun onItemClicked(position: Int, item: Orders) {

                }
            })
    }


}