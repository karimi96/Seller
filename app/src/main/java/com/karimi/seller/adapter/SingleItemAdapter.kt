package com.karimi.seller.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karimi.seller.R
import kotlinx.android.synthetic.main.list_item_spinner.view.*


class SingleItemAdapter(
    val context: Context,
    val list: ArrayList<String>,
    val listener: Listener
): RecyclerView.Adapter<SingleItemAdapter.ListViewHolder>() {


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener{
        fun onItemClicked(position: Int, string: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_spinner, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = holder.itemView
        val model = list[position]

        item.textView.text = model
        item.textView.setOnClickListener { listener.onItemClicked(position, model) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(string: String){
        list.add(string)
        notifyDataSetChanged()
    }
}