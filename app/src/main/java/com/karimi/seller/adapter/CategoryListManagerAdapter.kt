package com.karimi.seller.adapter

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karimi.seller.R
import com.karimi.seller.model.Category
import kotlinx.android.synthetic.main.list_item_category_category.view.*

class CategoryListManagerAdapter(
    val context: Context,
    val list: ArrayList<Category>,
    val listener: Listener
) : RecyclerView.Adapter<CategoryListManagerAdapter.ListViewHolder>() {

    private var packId = -1

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener{
        fun onItemClicked(position: Int, item: Category)
        fun onLongItemClicked(position: Int, item: Category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_category_category, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables", "ResourceAsColor")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = holder.itemView
        val model = list[position]

        if (model.status == 0) item.alpha = 0.5f
        else item.alpha = 1f

        item.title_category.text = model.name
        if (!model.content.isNullOrEmpty()){
            item.content_category.text = model.content

//            item.box_content.visibility = View.VISIBLE
        }
//        else item.box_content.visibility = View.GONE


        if (!model.image.isNullOrEmpty()){
            Glide.with(context).load(model.image).into(item.image_category)
//            item.image_category.visibility = View.VISIBLE
        }else{
//            item.image_category.setBackgroundColor(R.color.back_order)
            item.image_category.backgroundTintList = context.resources.getColorStateList(R.color.back_order)
            item.image_category.scaleType = null
            Glide.with(context).load(context.getDrawable(R.drawable.pic_picture)).into(item.image_category)
        }

        item.setOnClickListener {
            listener.onItemClicked(position,model)
        }

        item.setOnLongClickListener {
            listener.onLongItemClicked(position,model)
            return@setOnLongClickListener false
        }
    }

    fun addItem(category: Category){
        list.add(list.size,category)
        notifyItemInserted(list.size)
    }

    fun addItem(category: Category, position: Int){
        try {
            if (position == -1) addItem(category)
            else {
                list[position] = category
                notifyItemChanged(position,category)
            }
        }catch (e:Exception){
            Log.e(TAG, "addItem: ",e )
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list : List<Category>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}