package com.karimi.seller.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.karimi.seller.R
import com.karimi.seller.helper.App
import com.karimi.seller.model.Orders
import kotlinx.android.synthetic.main.list_item_order.view.*

class OrdersListAdapter(
    val context: Context,
    val list: ArrayList<Orders>,
    val listener: Listener
) : RecyclerView.Adapter<OrdersListAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener {
        fun onItemClicked(position: Int, item: Orders)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_order, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = holder.itemView
        val model = list[position]

        item.title_order_name.text = context.getString(
            R.string.order_customer_name,
            if (model.customer_name.isNullOrEmpty()) {
                "#${model.id}"
            } else model.customer_name
        )

        if (model.create_at != null) item.date_order.text =
            App.getFormattedDate(model.create_at!!.time)
        item.amount_order.text = App.priceFormat(model.totla_all, true)

        item.background_order2.backgroundTintList = if (model.status == 1) {
            ContextCompat.getColorStateList(context, R.color.white)
        } else ContextCompat.getColorStateList(context, R.color.back_order)

        item.setOnClickListener {
            listener.onItemClicked(position, model)
        }
    }

    fun add(item: Orders) {
        list.add(list.size, item)
        notifyItemInserted(list.size)
    }

    fun add(item: Orders, position: Int) {
        if (position == -1) add(item)
        else {
            Log.e("qqq", "addItem status is pos: $position")
            list[position] = item
            notifyItemChanged(position, item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Orders>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}