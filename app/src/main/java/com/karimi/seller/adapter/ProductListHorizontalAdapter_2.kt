package com.karimi.seller.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.marginBottom
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karimi.seller.R
import com.karimi.seller.helper.App
import com.karimi.seller.model.Product
import kotlinx.android.synthetic.main.list_item_financial_report.view.*



class ProductListHorizontalAdapter_2(
    val context: Context,
    val list: ArrayList<Product>,
    val listener: Listener
) : RecyclerView.Adapter<ProductListHorizontalAdapter_2.ListViewHolder>() {

    private var packId = -1

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener{
        fun onItemClicked(position: Int, product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_financial_report, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = holder.itemView
        val model = list[position]

        try {
            item.title_finance_product.text = model.name

            val price_sale = if (model.price_sale!! > 0 ) model.price_sale else model.price_sale_on_product
            item.content_finance.text = App.priceFormat(price_sale!!)

        }catch (e : Exception){
        }

        if (!model.image_defult.isNullOrEmpty()){
            Glide.with(context).load(model.image_defult).into(item.image_finance_p)
            item.image_finance_p.scaleType = ImageView.ScaleType.CENTER_CROP
        }else{
            Glide.with(context).load(context.getDrawable(R.drawable.pic_picture)).into(item.image_finance_p)
            item.image_finance_p.setBackgroundResource(R.color.back_order)
            item.image_finance_p.scaleType = ImageView.ScaleType.FIT_CENTER
            val param =  item.image_finance_p.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(6,4,6,4)
            item.image_finance_p.layoutParams = param
        }

        item.setOnClickListener {
            listener.onItemClicked(position,model)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list : List<Product>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}