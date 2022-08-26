package com.karimi.seller.activity.category

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.karimi.seller.R
import com.karimi.seller.adapter.CategoryListManagerAdapter
import com.karimi.seller.dialog.InsertCategoryDialog
import com.karimi.seller.helper.App
import com.karimi.seller.model.Category
import com.karimi.seller.model.TagList
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity(),InsertCategoryDialog.Listener {
    private var _ID_MOTHER = 0
    private var listCategoryForBack = ArrayList<TagList>()
    private var adapter: CategoryListManagerAdapter? = null
    private var dialog_category: InsertCategoryDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        initToolbar(getString(R.string.categorys))
        initAdapterCategory()
        initCategory()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                dialog_category?.initImage(resultUri)
//                _IMAGE_DEFULT_PATH = App.saveFile(App.getByte(resultUri)) // it's mohandes commited
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    private fun initToolbar(title: String) {

        plus_category.setOnClickListener {
            dialog_category = InsertCategoryDialog(
                this@CategoryActivity,
                null, -1, _ID_MOTHER, this
            )
            dialog_category!!.window?.setBackgroundDrawableResource(R.drawable.item_border_btn)
            dialog_category?.show()
        }


//        toolbar.title.text = title
//        toolbar.ic_back.visibility = View.VISIBLE
//        toolbar.ic_back.setOnClickListener { onBackPressed() }
//
//        toolbar.ic_add.visibility = View.VISIBLE
//        toolbar.ic_add.setOnClickListener {
//            dialog_category = InsertCategoryDialog(this@CategoryActivity,
//                null,-1,_ID_MOTHER,this)
//            dialog_category?.show()
//        }
    }



    private fun initAdapterCategory(){
        adapter = CategoryListManagerAdapter(this,
            ArrayList(selectCategory(0)),
            object : CategoryListManagerAdapter.Listener {
                override fun onItemClicked(position: Int, item: Category) {
                    _ID_MOTHER = item.id!!
                    listCategoryForBack.add(listCategoryForBack.size, TagList(item.name,item.id_mother.toString()))
                    initVisibilityIcBack()
                    adapter?.updateList(selectCategory(item.id!!))
                }

                override fun onLongItemClicked(position: Int, category: Category) {
                    dialog_category = InsertCategoryDialog(this@CategoryActivity,
                        category,position,category.id_mother!!,this@CategoryActivity)
                    dialog_category?.show()
                }
            })
    }

    private fun initCategory(){
//        tv_back_category.setOnClickListener {
//            if (!listCategoryForBack.isNullOrEmpty()){
//                val pos = listCategoryForBack.size-1
//                _ID_MOTHER = listCategoryForBack[pos].tag!!.toInt()
//                adapter?.updateList(selectCategory(listCategoryForBack[pos].tag!!.toInt()))
//                listCategoryForBack.removeAt(pos)
//            }
//            initVisibilityIcBack()
//        }
        recycler_category.adapter = adapter
    }

    private fun initVisibilityIcBack(){
//        tv_back_category.visibility = if (listCategoryForBack.isNullOrEmpty()) View.GONE else View.VISIBLE
//        if (tv_back_category.visibility == View.VISIBLE){
//            tv_back_category.setText(listCategoryForBack[listCategoryForBack.size-1].title)
//        }
    }
    private fun selectCategory(idMother: Int) : List<Category>{
        val l = App.database.getAppDao().selectUnderCategory(App.branch(),idMother)
        val m = ArrayList<Category>()
        for (i in l.listIterator()){
            val sizeOfUnderCategory = App.database.getAppDao().sizeCategory(App.branch(), i.id!!)
            val content = getString(R.string.under_category_by_value,
                if (sizeOfUnderCategory == 0) getString(R.string.dont_have)
                else sizeOfUnderCategory.toString()
            )
            m.add(Category(i.id,i.id_mother,i.name, content, i.image,i.branch,i.status))
        }
        return m
    }

    /**
     * Listener
     * */
    override fun chooseImage(dialog: AlertDialog) {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this@CategoryActivity)
    }

    override fun insert(dialog: AlertDialog, category: Category, position: Int) {
        category.id = App.database.getAppDao().insertCategory(category).toInt()
        adapter?.addItem(category, position)
        dialog.dismiss()
    }



}


