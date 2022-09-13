package com.karimi.seller.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karimi.seller.R
import com.karimi.seller.helper.App
import com.karimi.seller.model.Orders
import kotlinx.android.synthetic.main.list_item_last_order.view.*


class OrderWaitingAdapter(
    val context: Context,
    private val list: ArrayList<Orders>,
    private val listener: Listener?
): RecyclerView.Adapter<OrderWaitingAdapter.ListViewHolder>() {


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener{
        fun onItemClicked(position: Int, item: Orders)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_last_order, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = holder.itemView
        val model = list[position]

        item.title_last_order.text = if (model.customer_name.isNullOrEmpty()) "سفارش #${model.id}" else model.customer_name
        item.content_last_order.text = App.priceFormat(model.totla_all)

        if (listener != null) item.setOnClickListener { listener.onItemClicked(position, model) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: Orders){
        list.add(item)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(items: ArrayList<Orders>){
        this.list.clear()
        this.list.addAll(items)
        notifyDataSetChanged()
    }
}