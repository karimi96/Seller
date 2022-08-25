package com.karimi.seller.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.karimi.seller.R
import com.karimi.seller.model.MainModel
import kotlinx.android.synthetic.main.list_item_main.view.*


class ItemMainAdapter(
    val context: Context,
    val list: ArrayList<MainModel>,
    val listener: Listener?
) : RecyclerView.Adapter<ItemMainAdapter.ListViewHolder>() {


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener {
        fun onItemClicked(position: Int, item: MainModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_main, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = holder.itemView
        val model = list[position]

        item.title_main.text = model.title
        if (!model.cover.isNullOrEmpty()) item.desc.text = model.cover
        if (!model.content.isNullOrEmpty()) item.content.text = model.content
        if (model.image != null) item.image.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                model.image!!
            )
        )

        if (listener != null) item.setOnClickListener { listener.onItemClicked(position, model) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: MainModel) {
        list.add(item)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(items: ArrayList<MainModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }
}