package com.karimi.seller.dialog

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.karimi.seller.R
import com.karimi.seller.helper.App
import com.karimi.seller.helper.Session
import com.karimi.seller.model.Category
import kotlinx.android.synthetic.main.dialog_add_category.*


class InsertCategoryDialog(
    context: Context, val _category: Category?, val _position: Int,
    val _id_mother: Int, val listener: Listener
) : AlertDialog(context) {

    private var _ID = -1
    private var _IMAGE_PATH: String? = null
    private var _POS = -1


    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_category)
        this.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        Log.e(
            "qqq",
            "InsertCategoryDialog onCreate: id: $_ID - id_mother: $_id_mother - image: $_IMAGE_PATH - pos: $_POS "
        )
        initOnClick()
        if (_category != null) setValue(_category, _position)
    }

    fun initImage(resultUri: Uri) {
        fab_dialog.visibility = View.GONE
        image_add_category.visibility = View.VISIBLE
        Glide.with(context).load(resultUri).into(image_add_category)
        _IMAGE_PATH = App.saveFile(App.getByte(resultUri))
//        ic_delete.visibility = View.VISIBLE
    }

    interface Listener {
        fun chooseImage(dialog: AlertDialog)
        fun insert(dialog: AlertDialog, category: Category, position: Int)
    }

    private fun initOnClick() {
        submit_category.btn.setOnClickListener {
            if (formIsValid()) listener.insert(this, getValue(), _POS)
        }


        fab_dialog.setOnClickListener {
            listener.chooseImage(this)
        }

//        image_add_category.setOnClickListener {
//            listener.chooseImage(this)
//        }

//        ic_delete.setOnClickListener {
//            _IMAGE_PATH = null
//            image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_add_photo_alternate_black_24dp))
//            ic_delete.visibility = View.GONE
//        }
    }

    private fun formIsValid(): Boolean {
        if (App.getString(edt_name_add_c).isNullOrEmpty()) {
            edt_name_add_c.error = context.resources.getString(R.string.not_valid)
            return false
        }
        return true
    }

    private fun setValue(category: Category, position: Int) {
        _POS = position
        if (category.id != null) _ID = category.id!!

        if (!category.name.isNullOrEmpty()) edt_name_add_c.setText(category.name)
//        if (!category.content.isNullOrEmpty()) edt_content.setText(category.content)
        cb_add_c.isChecked = category.status != null && category.status == 1

        if (!category.image.isNullOrEmpty()) {
            fab_dialog.visibility = View.GONE
            image_add_category.visibility = View.VISIBLE
            Glide.with(context).load(category.image).into(image_add_category)
            _IMAGE_PATH = category.image
//            ic_delete.visibility = View.VISIBLE
        }
    }

    private fun getValue(): Category {
        val category = Category()
        if (_ID != -1) category.id = _ID
        category.id_mother = _id_mother
        category.name = App.getString(edt_name_add_c)
//        category.content = App.getString(edt_content)
        category.image = _IMAGE_PATH
        category.branch = Session.getInstance().branch
        category.status = if (cb_add_c.isChecked) 1 else 0
        return category
    }
}