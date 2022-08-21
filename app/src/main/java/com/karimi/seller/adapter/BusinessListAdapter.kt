package com.karimi.seller.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karimi.seller.R
import com.karimi.seller.model.Business
import kotlinx.android.synthetic.main.item_single_business.view.*

class BusinessListAdapter(val context: Context,
                          val list: ArrayList<Business>,
                          val listener: Listener?
) : RecyclerView.Adapter<BusinessListAdapter.ListViewHolder>() {

    private var position_selected = 0

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener{
        fun onEmpty(size:Int)
        fun onItemClicked(position: Int, item: Business)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_single_business, parent, false))
    }

    override fun getItemCount(): Int {
        listener?.onEmpty(list.size)
        return list.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ListViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = holder.itemView
        val model = list[position]

        item.texttttt.text = model.business_name
        if (listener != null){
            item.setOnClickListener {
                listener.onItemClicked(position,model)
            }
        }
    }

    fun addItem(item: Business){
        list.add(list.size,item)
        notifyItemInserted(list.size)
    }

    fun addItem(item: Business, position: Int){
        if (position == -1) addItem(item)
        else {
            Log.e("qqq", "addItem status is pos: $position" )
            list[position] = item
            notifyItemChanged(position,item)
        }
    }

}