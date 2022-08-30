package com.karimi.seller.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.karimi.seller.R
import com.karimi.seller.activity.category.CategoryActivity
import com.karimi.seller.activity.customer.CustomerActivity
import com.karimi.seller.activity.finance.FinanceActivity
import com.karimi.seller.activity.order.OrdersActivity
import com.karimi.seller.activity.product.ProductActivity
import com.karimi.seller.activity.setting.SettingActivity
import com.karimi.seller.activity.stock.StockActivity
import com.karimi.seller.activity.support.SupportActivity
import com.karimi.seller.adapter.ItemMainAdapter
import com.karimi.seller.adapter.OrderWaitingAdapter
import com.karimi.seller.dialog.BusinessMenuDialog
import com.karimi.seller.helper.App
import com.karimi.seller.helper.Config.ORDER_STATUS_WAITING
import com.karimi.seller.helper.Session
import com.karimi.seller.model.Business
import com.karimi.seller.model.MainModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.box_detail_main_activity.*
import kotlinx.android.synthetic.main.box_order_waiting_.*
import kotlinx.android.synthetic.main.toolbar_main_activity.view.*

class MainActivity : AppCompatActivity() ,ItemMainAdapter.Listener{
    private var adapterWaiting : OrderWaitingAdapter? = null
    private var adapterMain : ItemMainAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var transition: Transition
//        transition = Slide(Gravity.TOP)
//        transition.duration = 1000
//        TransitionManager.beginDelayedTransition(relative_main, transition)
//        relative_main.visibility = View.VISIBLE

        scrollMain.post { scrollMain.fullScroll(View.FOCUS_UP) }


        initToolbar()
        initRecyclerView()




    }


    override fun onResume() {
        super.onResume()
        initData()
    }


    private fun pageAnimation() {
//        anim_grouping.translationY = 0f
//        anim_grouping.animate().translationYBy(-10f).duration = 1500
////        anim_grouping.resources.getAnimation(R.anim.up_to_down)


        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.up_to_down)
        slideAnimation.duration = 1500
        anim_grouping.startAnimation(slideAnimation)
        anim_grouping.visibility = View.VISIBLE

    }


    private fun initToolbar(){
        toolbar.content.setOnClickListener {
//            pageAnimation()
            BusinessMenuDialog(this,object : BusinessMenuDialog.Listener{
                override fun onEditBusiness(dialog: BusinessMenuDialog, business: Business?) {
                    businessSetText()
                }
                override fun onBusinessList(dialog: BusinessMenuDialog, business: Business) {
                    initData()
                }
            }).show(supportFragmentManager,"menu")
        }
    }


    private fun initData(){
        businessSetText()
        orderWaitingVisibility()
        updateItemMainModel()
    }

    private fun businessSetText(){
        toolbar.title.text = resources.getString(R.string.welcome_owner, Session.getInstance().businessOwnerName)
        toolbar.content.text = resources.getString(R.string.welcome_to_business,Session.getInstance().businessName)
    }


    private fun orderWaitingVisibility(){
        val arrayList = ArrayList(App.database.getAppDao().selectOrders(App.branch(),ORDER_STATUS_WAITING))
        if (!arrayList.isNullOrEmpty()){
            adapterWaiting?.updateList(arrayList)
            order_waiting.visibility = View.VISIBLE
        }else if (order_waiting.visibility != View.GONE) order_waiting.visibility = View.GONE
    }


    private fun updateItemMainModel(){
        val branch = Session.getInstance().branch

        val array_product = ArrayList(App.database.getAppDao().productCount(branch))
        var qalamkala = 0.0
        var sarmayeh = 0.0
        for (i in array_product.indices){
            qalamkala += array_product[i].stock?:0.0
            sarmayeh += qalamkala * (array_product[i].price_buy?:0.0)
        }
        val product_count = array_product.size

        val arrayList = ArrayList<MainModel>()
        arrayList.add(MainModel(R.drawable.ic_extension,"محصولات","محصولات ثبت شده",
            "$product_count محصول",
            ProductActivity::class.java))

        arrayList.add(MainModel(R.drawable.ic_shopping,"سفارشات","سفارشات انجام شده",
            "${App.database.getAppDao().orderCount(branch)} سفارش",
            OrdersActivity::class.java))
        arrayList.add(MainModel(R.drawable.ic_category,"دسته‌بندی","دسته‌بندی های ثبت شده",
            "${App.database.getAppDao().categoryCount(branch)} دسته‌بندی",
            CategoryActivity::class.java))
        arrayList.add(MainModel(R.drawable.ic_account1,"مشتریان","خریداران شما",
            "${App.database.getAppDao().customerCount(branch)} مشتری",
            CustomerActivity::class.java))
        arrayList.add(MainModel(R.drawable.ic_storefront,"گزارش انبار","کالاهای موجود",
            "${App.priceFormat(qalamkala)} قلم‌",
            StockActivity::class.java))
        arrayList.add(MainModel(R.drawable.ic_monetization,"گزارش مالی","سرمایه موجود",
            "${App.priceFormat(sarmayeh,true)}",
            FinanceActivity::class.java))
        arrayList.add(MainModel(R.drawable.ic_setting,"تنظیمات", "مالیات، واحدپول و..", "", SettingActivity::class.java))
        arrayList.add(MainModel(R.drawable.ic_import,"پشتیبانی","راه های ارتباطی","", SupportActivity::class.java))

        adapterMain?.updateList(arrayList)
    }


    private fun initRecyclerView(){
//        adapterWaiting = OrderWaitingAdapter(this, ArrayList(),object : OrderWaitingAdapter.Listener{
//            override fun onItemClicked(position: Int, item: Orders) {
//                OrderViewDialog(this@MainActivity,item.id!!,null, null)
//                    .show(supportFragmentManager,"order_view")
//            }
//        })
//        recyclerView_order_waiting.adapter = adapterWaiting

        adapterMain = ItemMainAdapter(this , ArrayList(),this)
        recyclerView_main.adapter = adapterMain
    }

    override fun onItemClicked(position: Int, item: MainModel) {
        startActivity(Intent(this, item.action))
    }


//    updateItemMainModel()
//    barChartAdapter()
//    initOnClick()

}