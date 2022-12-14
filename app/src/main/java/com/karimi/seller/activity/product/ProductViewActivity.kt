package com.karimi.seller.activity.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.karimi.seller.R
import com.karimi.seller.adapter.CustomerListHorizontalAdapter
import com.karimi.seller.adapter.TagAdapter
import com.karimi.seller.helper.App
import com.karimi.seller.model.Product
import com.karimi.seller.model.TagList
import kotlinx.android.synthetic.main.activity_product_view.*
import kotlinx.android.synthetic.main.include_customer_product_view.*
import kotlinx.android.synthetic.main.include_recycler_product_view.*
import kotlinx.android.synthetic.main.toolbar_product_view.*

class ProductViewActivity : AppCompatActivity() {
    private var product_id : Int? = null
    private var position : Int? = null
    private var this_product : Product? = null
//    private var productActivity : ProductActivity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_view)

        if (intent.extras != null){
            product_id = intent.getIntExtra("product_id",-1)
            position = intent.getIntExtra("pos",-1)
        }

      this_product = App.database.getAppDao().selectProduct(App.branch(), product_id!!)


        initData()
        initOnClick()
        initAdapterTagList()
        initRecyclerView()
//        chart_bar_price.barChartAdapter()
        cubicChart.setTitle("نمودار فروش 3 ماهه")

    }


    interface Listener{
        fun onEditProduct(product: Product?, position: Int)
    }



    private fun initData(){
//        tv_product_name.text = "#${this_product?.id}"
        title_product_view.text = this_product?.name
        arrayOf(title_product_view , back_product_view).forEach { it.setOnClickListener { onBackPressed() } }

        if (!this_product?.image_defult.isNullOrEmpty()){
            Glide.with(this).load(this_product?.image_defult).into(image_product_view)
            image_product_view.scaleType = ImageView.ScaleType.CENTER_CROP
        }else {
            image_product_view.scaleType = ImageView.ScaleType.FIT_CENTER
            Glide.with(this).load(getDrawable(R.drawable.pic_picture)).into(image_product_view)
        }

        tv_date.text = "در تاریخ ${ App.getFormattedDate(this_product?.updated_at) }ویرایش شده \n در تاریخ ${ App.getFormattedDate(this_product?.created_at) } ثبت شده"
    }



    private fun initOnClick(){
//        ic_close.setOnClickListener {
//            dismiss()
//        }

        tv_edit_p_view.setOnClickListener {
//            listener?.onEditProduct(this,this_product, position)
//            ListProductActivity().resultUpdate
            App.toast("edit btn clicked")
            var i = Intent(this , AddNewProductActivity::class.java)
            i.putExtra("product_id", this_product!!.id)
            i.putExtra("product_position", position)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
//            productActivity?.resultUpdate?.launch(i)
        }
    }

    private fun initAdapterTagList(){
        val array_tag = ArrayList<TagList>()
        array_tag.add(TagList("۲۹۰ عدد موجودی"))
        array_tag.add(TagList("۹۲۰,۰۰۰ تومان سرمایه"))
        array_tag.add(TagList("۲۰۰ تومان سود"))
        array_tag.add(TagList("۵۰۰ تومان ارزانتر"))
        array_tag.add(TagList("۹۲ مرتبه سفارش داده شده"))
        array_tag.add(TagList("۹۸۰ عدد فروش رفته"))
        array_tag.add(TagList("#3881492193493"))
        array_tag.add(TagList("آخرین بار در ۱۴۰۱/۰۲/۰۶ فروخته شده"))

        recyclerView_tag.adapter = TagAdapter(this, array_tag, null, 2)
    }

    private fun initRecyclerView(){
        val arrayList = ArrayList<TagList>()
        arrayList.add(TagList("امین گلی","۲۸۰ عدد"))
        arrayList.add(TagList("حسین یوسفی","۱۸۲ عدد"))
        arrayList.add(TagList("اصغر حاجیان","۱۱۰ عدد"))
        arrayList.add(TagList("سارا عبدالکریمی","۹۸ عدد"))
        arrayList.add(TagList("حسن حاجی پور","۸۰ عدد"))
        recyclerView_customer.adapter = CustomerListHorizontalAdapter(this,arrayList,
            object : CustomerListHorizontalAdapter.Listener{
                override fun onItemClicked(position: Int, item: TagList) {
                    App.toast("clicked  " + item.title)
                }

            })
    }
}