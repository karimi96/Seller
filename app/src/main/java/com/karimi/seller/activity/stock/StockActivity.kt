package com.karimi.seller.activity.stock

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.karimi.seller.R
import com.karimi.seller.adapter.ProductListAdapter_2
import com.karimi.seller.adapter.TagAdapter
import com.karimi.seller.adapter.TagInfoAdapter
import com.karimi.seller.model.TagList
import kotlinx.android.synthetic.main.activity_stock.*
import kotlinx.android.synthetic.main.include_toolbar_list_anbar.*

class StockActivity : AppCompatActivity() {

    private var adapter : ProductListAdapter_2? =null
    private var adapterTag : TagInfoAdapter? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        initToolbar(getString(R.string.stock_toolbar))
        initAdapterTagList()
        initTagInfo()


    }


    private fun initToolbar(title: String) {
//        toolbar.title.text = title
//        toolbar.ic_back.visibility = View.VISIBLE
//        toolbar.ic_back.setOnClickListener { onBackPressed() }
    }


    val array_tag = ArrayList<TagList>()
    private fun initAdapterTagList(){
        array_tag.add(TagList("درحال فروش","all"))
        array_tag.add(TagList("درحال انتقضا", "all"))
        array_tag.add(TagList("درحال اتمام", "all"))
        array_tag.add(TagList("منقضی شده", "all"))
        array_tag.add(TagList("تمام شده", "all"))
        array_tag.add(TagList("بیشترین فروش", "all"))
        array_tag.add(TagList("کمترین فروش","all"))
        array_tag.add(TagList("بیشترین سود","all"))
        array_tag.add(TagList("کمترین سود", "all"))
        array_tag.add(TagList("بیشترین موجودی", "all"))

        adapterTag = TagInfoAdapter(this,
            array_tag,
            object : TagInfoAdapter.Listener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClicked(position: Int, item: TagList) {
//                    adapter?.updateList(selectOrder(item.tag!!))
                }
            })

        recycler_tag_stock.adapter = adapterTag
    }


    private fun initTagInfo(){
        val array_tag_info = ArrayList<TagList>()
        array_tag_info.add(TagList("۳۰۰ قلم کالا"))
        array_tag_info.add(TagList("۱۲۰ محصول فعال"))
        array_tag_info.add(TagList("۲۹۰,۰۰۰,۰۰۰ تومان سرمایه انبار"))
        recyclerView_info.adapter = TagAdapter(this,array_tag_info,null)
    }


}