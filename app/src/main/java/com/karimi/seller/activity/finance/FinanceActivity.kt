package com.karimi.seller.activity.finance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R
import com.karimi.seller.adapter.TagAdapter
import com.karimi.seller.model.TagList
import kotlinx.android.synthetic.main.activity_customer_view.*
import kotlinx.android.synthetic.main.include_toolbar_finance.*

class FinanceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

        chart_bar_price.barChartAdapter()
        initTagInfo()




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

}