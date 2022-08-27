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
import kotlinx.android.synthetic.main.list_item_favorid_p.view.*



class ProductListHorizontalAdapter_3(
    val context: Context,
    val list: ArrayList<Product>,
    val listener: Listener
) : RecyclerView.Adapter<ProductListHorizontalAdapter_3.ListViewHolder>() {

    private var packId = -1

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener {
        fun onItemClicked(position: Int, product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_favorid_p, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = holder.itemView
        val model = list[position]

        try {
            item.title_favorite.text = model.name

            val price_sale =
                if (model.price_sale!! > 0) model.price_sale else model.price_sale_on_product
            item.content_favorite.text = App.priceFormat(price_sale!!)

        } catch (e: Exception) {
        }

        if (!model.image_defult.isNullOrEmpty()) {
//            item.image_favorite.visibility = View.VISIBLE
            Glide.with(context).load(model.image_defult).into(item.image_favorite)
        }
//        else item.image_favorite.visibility = View.GONE

        item.setOnClickListener {
            listener.onItemClicked(position, model)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Product>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}