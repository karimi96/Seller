package com.karimi.seller.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.karimi.seller.R
import com.karimi.seller.model.Category
import kotlinx.android.synthetic.main.item_select_category_list.view.*

class SelectCategoryAdapter(
    val context: Context,
    val list: ArrayList<Category>,
    val category_is_chose: ArrayList<Category>,
    val listener: Listener
) : RecyclerView.Adapter<SelectCategoryAdapter.ListViewHolder>() {

    private var position_selected = -1

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener{
        fun onItemClicked(position: Int, item: Category)
        fun onItemCheckBox(position: Int, item: Category, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_select_category_list, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ListViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = holder.itemView
        val model = list[position]

        setItemSelected(model.id,item.checkbox)

        item.checkbox.text = model.name

        item.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            listener.onItemCheckBox(position,model,isChecked)
        }

        item.box.setOnClickListener {
            listener.onItemClicked(position,model)
        }

        item.icon.setOnClickListener {
            listener.onItemClicked(position,model)
        }
    }

    private fun setItemSelected(id_category: Int?, checkBox : CheckBox){
        for (i in 0 until category_is_chose.size){
            if (id_category == category_is_chose[i].id){
                checkBox.isChecked = true
                return
            }
        }
        checkBox.isChecked = false
    }

    fun addItem(item: Category){
        list.add(list.size,item)
        notifyItemInserted(list.size)
    }

    fun addItem(item: Category, position: Int){
        if (position == -1) addItem(item)
        else {
            Log.e("qqq", "addItem status is pos: $position" )
            list[position] = item
            notifyItemChanged(position,item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyAllData(){
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list : List<Category>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}