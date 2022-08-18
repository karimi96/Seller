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
import kotlinx.android.synthetic.main.list_item_product.view.*



class ProductListManagerAdapter(
    val context: Context,
    private val list: ArrayList<Product>,
    private val listener: Listener
) : RecyclerView.Adapter<ProductListManagerAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener{
        fun onEmpty(size : Int)
        fun onItemClicked(position: Int, product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false))
    }

    override fun getItemCount(): Int {
        listener.onEmpty(list.size)
        return list.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = holder.itemView
        val model = list[position]

        item.title.text = model.name
        item.price_sela.text = App.priceFormat(model.price_sale!!, true)

        if (!model.image_defult.isNullOrEmpty()){
            item.image.visibility = View.VISIBLE
            Glide.with(context).load(model.image_defult).into(item.image)
        }else{
            item.image.visibility = View.VISIBLE
            Glide.with(context).load(context.getDrawable(R.drawable.pic_defult)).into(item.image)
//            item.image.visibility = View.GONE
        }

        item.setOnClickListener {
            listener.onItemClicked(position,model)
        }
    }

    fun add(item : Product){
        this.list.add(list.size,item)
        notifyItemInserted(list.size)
    }

    fun update(position: Int, item : Product){
        this.list[position] = item
        notifyItemChanged(position,item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list : List<Product>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}