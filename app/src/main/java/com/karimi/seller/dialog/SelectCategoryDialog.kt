package com.karimi.seller.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.DialogFragment
import com.karimi.seller.R
import com.karimi.seller.adapter.SelectCategoryAdapter
import com.karimi.seller.helper.App
import com.karimi.seller.helper.Session
import com.karimi.seller.model.Category
import com.karimi.seller.model.TagList
import kotlinx.android.synthetic.main.dialog_select_category.*


class SelectCategoryDialog(
    val _context: Context,
    val _categoryList: ArrayList<Category>,
    val _listener: Listener
) : DialogFragment(), SelectCategoryAdapter.Listener {

    private var adapter: SelectCategoryAdapter? = null
    private var _categoryListDatabase: ArrayList<Category> = ArrayList()
    private var listCategoryForBack = ArrayList<TagList>()
    private var id_mother_category = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_select_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.isCancelable = true

        adapter = SelectCategoryAdapter(_context, _categoryListDatabase, _categoryList, this)
        recyclerView.adapter = adapter
        showNewList(id_mother_category)
        initOnClick()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.setGravity(Gravity.CENTER)
    }

    interface Listener {
        fun onSubmit(dialog: SelectCategoryDialog, list: ArrayList<Category>?)
    }

    private fun initOnClick() {
        tv_back.setOnClickListener {
            if (!listCategoryForBack.isNullOrEmpty()) {
                val pos = listCategoryForBack.size - 1
                id_mother_category = listCategoryForBack[pos].tag!!.toInt()
                showNewList(listCategoryForBack[pos].tag!!.toInt())
                listCategoryForBack.removeAt(pos)
            }
            initVisibilityIcBack()
        }

        ic_add.setOnClickListener {
            addCategory()
        }

        edt_category_name.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE,
                EditorInfo.IME_ACTION_GO,
                EditorInfo.IME_ACTION_SEND -> {
                    addCategory()
                }
            }
            return@setOnEditorActionListener false
        }

        tv_submit.setOnClickListener {
            _listener.onSubmit(this, _categoryList)
        }
    }

    private fun showNewList(id_category: Int) {
        id_mother_category = id_category
        recyclerView.removeAllViews()
        _categoryListDatabase.clear()
        _categoryListDatabase.addAll(
            ArrayList(
                App.database.getAppDao().selectUnderCategory(App.branch(), id_category)
            )
        )
        adapter?.notifyAllData()
    }

    private fun addItemCategoryListManager(item: Category) {
        for (i in 0 until _categoryList.size) {
            if (_categoryList[i].id == item.id) return
        }
        _categoryList.add(item)
    }

    private fun removeItemCategoryListManager(item: Category) {
        for (i in 0 until _categoryList.size) {
            if (_categoryList[i].id == item.id) {
                _categoryList.removeAt(i)
                return
            }
        }
    }

    private fun initVisibilityIcBack() {
        tv_back.visibility = if (listCategoryForBack.isNullOrEmpty()) View.GONE else View.VISIBLE
        if (tv_back.visibility == View.VISIBLE) {
            tv_back.setText(listCategoryForBack[listCategoryForBack.size - 1].title)
        }
    }

    private fun addCategory() {
        if (!App.getString(edt_category_name).isNullOrEmpty()) {
            adapter?.addItem(
                getCategory(
                    App.database.getAppDao().insertCategory(getCategory(null))
                )
            )
            edt_category_name.text.clear()
            recyclerView.smoothScrollToPosition(_categoryListDatabase.size)
        }
    }

    private fun getCategory(id: Long?): Category {
        val category = Category()
        if (id != null) category.id = id.toInt()
        category.id_mother = id_mother_category
        category.name = App.getString(edt_category_name)
        category.content = ""
        category.image = ""
        category.branch = Session.getInstance().branch
        category.status = 1
        return category
    }

    /**
     * Listener
     * */
    override fun onItemClicked(position: Int, item: Category) {
        listCategoryForBack.add(
            listCategoryForBack.size,
            TagList(item.name, item.id_mother.toString())
        )
        initVisibilityIcBack()

        addItemCategoryListManager(item)
        showNewList(item.id!!)
    }

    override fun onItemCheckBox(position: Int, item: Category, isChecked: Boolean) {
        Log.e("qqqamin-1", "category: ${item.name} id: ${item.id} - isChecked: ${isChecked}")
        if (isChecked) addItemCategoryListManager(item)
        else removeItemCategoryListManager(item)
    }
}