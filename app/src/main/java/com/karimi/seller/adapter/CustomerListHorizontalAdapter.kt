package com.karimi.seller.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karimi.seller.R
import com.karimi.seller.model.TagList
import kotlinx.android.synthetic.main.list_item_customer_product_view.view.*

class CustomerListHorizontalAdapter(val context: Context,
                                    val list: ArrayList<TagList>,
                                    val listener: Listener
) : RecyclerView.Adapter<CustomerListHorizontalAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener{
        fun onItemClicked(position: Int, item: TagList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_customer_product_view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = holder.itemView
        val model = list[position]

        item.title_customer_p_view.setText("${model.title}")
        item.content_customer_p_view.text = model.tag

        item.setOnClickListener {
            listener?.onItemClicked(position,model)
        }
    }

    fun addItem(item: TagList){
        list.add(list.size,item)
        notifyItemInserted(list.size)
    }

    fun addItem(item: TagList, position: Int){
        if (position == -1) addItem(item)
        else {
            Log.e("qqq", "addItem status is pos: $position" )
            list[position] = item
            notifyItemChanged(position,item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list : List<TagList>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}