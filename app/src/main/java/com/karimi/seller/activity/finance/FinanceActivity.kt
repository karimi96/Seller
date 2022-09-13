package com.karimi.seller.activity.finance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.karimi.seller.R
import com.karimi.seller.activity.product.ProductViewActivity
import com.karimi.seller.adapter.OrderWaitingAdapter
import com.karimi.seller.adapter.ProductListHorizontalAdapter_2
import com.karimi.seller.adapter.TagAdapter
import com.karimi.seller.helper.App
import com.karimi.seller.model.Orders
import com.karimi.seller.model.Product
import com.karimi.seller.model.TagList
import kotlinx.android.synthetic.main.activity_customer_view.*
import kotlinx.android.synthetic.main.activity_customer_view.cubicChart
import kotlinx.android.synthetic.main.activity_finance.*
import kotlinx.android.synthetic.main.include_financial_1.*
import kotlinx.android.synthetic.main.include_financial_2.*
import kotlinx.android.synthetic.main.toolbar_finance.*

class FinanceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

//        chart_bar_price.barChartAdapter()
        initToolbar()
        initTagInfo()
        initAdapterOrders()
        initAdapterProduct()
        cubicChart.setTitle("نمودار")

    }


    private fun initToolbar(){
        arrayOf(back_finance,tv_finance_toolbar).forEach { it.setOnClickListener { onBackPressed() } }
    }

    private fun initTagInfo(){
        val array_tag_info = ArrayList<TagList>()
        array_tag_info.add(TagList("درآمد نقدی ۲۹۰,۰۰۰ تومان"))
        array_tag_info.add(TagList("درآمد کارتخوان ۲۹۰,۰۰۰ تومان"))
        array_tag_info.add(TagList("نسیه ۲۹۰,۰۰۰ تومان"))
        array_tag_info.add(TagList("تخفیف ۲۹۰,۰۰۰ تومان"))
        array_tag_info.add(TagList("هزینه ارسال ۲۹۰,۰۰۰ تومان"))
        recyclerView_tag_info.adapter = TagAdapter(this,array_tag_info,null ,1)
    }


    private fun initAdapterOrders(){
        var list = ArrayList(App.database.getAppDao().selectOrders(App.branch()))
        if (list.size > 0 || list.isNotEmpty()){
            box_orders.visibility = View.VISIBLE
            recyclerView_order_financial.adapter = OrderWaitingAdapter(this, ArrayList(App.database.getAppDao().selectOrders(App.branch())),
                object : OrderWaitingAdapter.Listener{
                    override fun onItemClicked(position: Int, item: Orders) {

                    }
                })
        }

    }


    private fun initAdapterProduct(){
        var list = ArrayList(App.database.getAppDao().selectProduct(App.branch()))
        if (list.size > 0){
            box_product.visibility = View.VISIBLE
            recyclerView_product_stock.adapter = ProductListHorizontalAdapter_2(this,
                ArrayList(App.database.getAppDao().selectProduct(App.branch())),
                object : ProductListHorizontalAdapter_2.Listener {
                    override fun onItemClicked(position: Int, product: Product) {
                        var i = Intent(this@FinanceActivity, ProductViewActivity::class.java)
                        i.putExtra("product_id",product.id)
                        i.putExtra("pos",position)
                        startActivity(i)

//                    ProductViewDialog(this@FinanceActivity,product.id!!,position,null)
//                        .show(supportFragmentManager,"order_view")
                    }
                })
        }

    }


}