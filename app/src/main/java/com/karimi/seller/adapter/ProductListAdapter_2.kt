package com.karimi.seller.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karimi.seller.R
import com.karimi.seller.helper.App
import com.karimi.seller.model.Product
import kotlinx.android.synthetic.main.list_item_anbar3.view.*


class ProductListAdapter_2(
    val context: Context,
    val list: ArrayList<Product>,
    val listener: Listener
) : RecyclerView.Adapter<ProductListAdapter_2.ListViewHolder>() {

    private var packId = -1

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener{
        fun onItemClicked(position: Int, product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_stock3, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = holder.itemView
        val model = list[position]

        try {
            item.title_stock.text = model.name
            item.content_stock.text = App.priceFormat(model.price_sale_on_product!!)

        }catch (e : Exception){
        }

        if (!model.image_defult.isNullOrEmpty()){
//            item.image_stock.visibility = View.VISIBLE
            Glide.with(context).load(model.image_defult).into(item.image_stock)
        }else item.image_stock.setBackgroundResource(R.color.back_hint)


        item.setOnClickListener {
            listener.onItemClicked(position,model)
        }
    }
}