package com.karimi.seller.activity.finance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R
import com.karimi.seller.adapter.OrderWaitingAdapter
import com.karimi.seller.adapter.TagAdapter
import com.karimi.seller.helper.App
import com.karimi.seller.model.Orders
import com.karimi.seller.model.TagList
import kotlinx.android.synthetic.main.activity_customer_view.*
import kotlinx.android.synthetic.main.include_financial_2.*
import kotlinx.android.synthetic.main.include_toolbar_finance.*

class FinanceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

        chart_bar_price.barChartAdapter()
        initTagInfo()
        initAdapterOrders()




    }


    private fun initTagInfo(){
        val array_tag_info = ArrayList<TagList>()
        array_tag_info.add(TagList("درآمد نقدی ۲۹۰,۰۰۰ تومان"))
        array_tag_info.add(TagList("درآمد کارتخوان ۲۹۰,۰۰۰ تومان"))
        array_tag_info.add(TagList("نسیه ۲۹۰,۰۰۰ تومان"))
        array_tag_info.add(TagList("تخفیف ۲۹۰,۰۰۰ تومان"))
        array_tag_info.add(TagList("هزینه ارسال ۲۹۰,۰۰۰ تومان"))
        recyclerView_tag_info.adapter = TagAdapter(this,array_tag_info,null)
    }


    private fun initAdapterOrders(){
        recyclerView_order_financial.adapter = OrderWaitingAdapter(this, ArrayList(App.database.getAppDao().selectOrders(App.branch())),
            object : OrderWaitingAdapter.Listener{
                override fun onItemClicked(position: Int, item: Orders) {

                }
            })
    }

}