package com.karimi.seller.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.karimi.seller.R
import com.karimi.seller.model.TagList
import kotlinx.android.synthetic.main.list_item_state_order_.view.*

class TagInfoAdapter(
    val context: Context,
    val list: ArrayList<TagList>,
    val listener: Listener?,
    var tag : Int
) : RecyclerView.Adapter<TagInfoAdapter.ListViewHolder>() {

    var position_selected = 0

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener {
        fun onItemClicked(position: Int, item: TagList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_state_order_, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = holder.itemView
        val model = list[position]

        setItemSelected(
            position_selected != -1 && position_selected == position,
            item.background_order1, item.title_category_or
        )

        item.title_category_or.text = model.title
//        if (model.icon != null) {
//            item.image_icon.visibility = View.VISIBLE
//            item.image_icon.setImageDrawable(ContextCompat.getDrawable(context,model.icon!!))
//        }
//        else item.image_icon.visibility = View.GONE

        item.setOnClickListener {
            val back_position_selected = position_selected
            position_selected = position
            listener?.onItemClicked(position, model)
            notifyItemChanged(position_selected, model)
            notifyItemChanged(back_position_selected, model)
        }
    }

    fun addItem(item: TagList) {
        list.add(list.size, item)
        notifyItemInserted(list.size)
    }

    fun addItem(item: TagList, position: Int) {
        if (position == -1) addItem(item)
        else {
            Log.e("qqq", "addItem status is pos: $position")
            list[position] = item
            notifyItemChanged(position, item)
        }
    }

    fun removeSelection() {
        val p = position_selected
        position_selected = -1
        notifyItemChanged(p)
    }

    private fun setItemSelected(is_selected: Boolean, background: CardView, textView: TextView) {
        if (is_selected) {
            background.radius = 10F
            background.backgroundTintList = context.resources.getColorStateList(R.color.purple)
            textView.setTextColor(ContextCompat.getColor(context, R.color.white))

        } else {
            if (tag == 0){
                textView.setTextColor(ContextCompat.getColor(context, R.color.text_main))
                background.backgroundTintList = context.resources.getColorStateList(R.color.back_hint)
            }else if (tag == 1){
                textView.setTextColor(ContextCompat.getColor(context, R.color.text_main))
                background.backgroundTintList = context.resources.getColorStateList(R.color.white)

            }

        }
    }
}