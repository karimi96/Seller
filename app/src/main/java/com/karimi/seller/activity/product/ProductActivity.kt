package com.karimi.seller.activity.product


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.karimi.seller.R
import com.karimi.seller.adapter.CategoryListAdapter
import com.karimi.seller.adapter.ProductListManagerAdapter
import com.karimi.seller.helper.App
import com.karimi.seller.model.Category
import com.karimi.seller.model.Product
import com.karimi.seller.model.TagList
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.toolbar_product.*

class ProductActivity : AppCompatActivity() , ProductViewActivity.Listener{

    private var listCategoryForBack = ArrayList<TagList>()
    private var adapterProduct : ProductListManagerAdapter? = null
    private var adapterTagList : CategoryListAdapter? = null
    private var last_search : String? = null

    private val resultAdd =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data!= null){
                    if (result.data!!.extras!= null){
                        val product_id:Int = result.data!!.getIntExtra("product_id",-1)
                        adapterProduct?.add(App.database.getAppDao().selectProduct(App.branch(), product_id))
                    }
                }
            }
        }

     val resultUpdate =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data!= null){
                    if (result.data!!.extras!= null){
                        val product_id:Int = result.data!!.getIntExtra("product_id",-1)
                        val product_position:Int = result.data!!.getIntExtra("product_position",-1)
                        Log.e("qqqq", "id: ${product_id} - pos: ${product_position}")
                        adapterProduct?.update(product_position, App.database.getAppDao().selectProduct(App.branch(), product_id))
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        initToolbar()
        initAdapter()
        initRecyclerCategory()
        initRecyclerProduct()
    }

    private fun initToolbar(){
        title_product.text = resources.getString(R.string.toolbar_title_product)
        plus_product.setOnClickListener { startActivity(Intent(this,AddNewProductActivity::class.java)) }

        arrayOf(title_product,back_product).forEach { it.setOnClickListener { onBackPressed() } }





//        toolbar.ic_back.visibility = View.VISIBLE
//        toolbar.ic_back.setOnClickListener { onBackPressed() }
//        toolbar.ic_add.visibility = View.VISIBLE
//        toolbar.ic_add.setOnClickListener {
//            resultAdd.launch(Intent(this,ProductActivity::class.java))
//        }

//        toolbar.ic_search.visibility = View.VISIBLE
//        toolbar.ic_search.setOnClickListener {
//            toolbar.ic_back.visibility = View.GONE
//            toolbar.title.visibility = View.GONE
//            toolbar.ic_add.visibility = View.GONE
//            toolbar.edt_search.visibility = View.VISIBLE
//            toolbar.ic_close.visibility = View.VISIBLE
//            toolbar.edt_search.setSelection(0)
//        }
//        toolbar.ic_close.setOnClickListener {
//            toolbar.edt_search.text.clear()
//            toolbar.ic_back.visibility = View.VISIBLE
//            toolbar.title.visibility = View.VISIBLE
//            toolbar.ic_add.visibility = View.VISIBLE
//            toolbar.edt_search.visibility = View.GONE
//            toolbar.ic_close.visibility = View.GONE
//            App.closeKeyboard(this)
//        }

//        toolbar.edt_search.setOnEditorActionListener { v, actionId, event ->
//            when(actionId){
//                EditorInfo.IME_ACTION_DONE,
//                EditorInfo.IME_ACTION_GO,
//                EditorInfo.IME_ACTION_SEARCH->{
//                    searchProduct(App.getString(toolbar.edt_search))
//                    App.closeKeyboard(this)
//                }
//            }
//            return@setOnEditorActionListener false
//        }

    }

//    private fun initVisibilityIcBack(){
//        tv_back_category.visibility = if (listCategoryForBack.isNullOrEmpty()) View.GONE else View.VISIBLE
//        if (tv_back_category.visibility == View.VISIBLE){
//            tv_back_category.setText(listCategoryForBack[listCategoryForBack.size-1].title)
//        }
//    }

//    private fun searchProduct(q:String){
//        if (!q.isNullOrEmpty() && q != last_search){
//            last_search = q
//            adapterProduct?.updateList(App.database.getAppDao().searchSmallSizeProduct(App.branch(),q))
//            listCategoryForBack.clear()
//            listCategoryForBack.add( TagList(resources.getString(R.string.back),"0"))
//            initVisibilityIcBack()
//        }
//    }

    private fun initAdapter(){
        adapterTagList = CategoryListAdapter(this,
            ArrayList(App.database.getAppDao().selectUnderCategory(App.branch(),0)),
            object : CategoryListAdapter.Listener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClicked(position: Int, item: Category) {
                    listCategoryForBack.add(listCategoryForBack.size, TagList(item.name,item.id_mother.toString()))
//                    initVisibilityIcBack()

                    adapterProduct?.updateList(App.database.getAppDao().selectSmallSizeProductByCategory(App.branch(), item.id!!))
                    adapterTagList?.updateList(App.database.getAppDao().selectUnderCategory(App.branch(),item.id!!))
                }
            })

        adapterProduct = ProductListManagerAdapter(this,
            ArrayList(App.database.getAppDao().selectSmallSizeProduct(App.branch())),
            object : ProductListManagerAdapter.Listener{
                override fun onEmpty(size: Int) {
                    if (size == 0) statuser.onEmpty()
                    else statuser.onFinish()
                }
                override fun onItemClicked(position: Int, product: Product) {
                    var i = Intent(this@ProductActivity,ProductViewActivity::class.java)
                    i.putExtra("product_id",product.id)
                    i.putExtra("pos",position)
                    startActivity(i)

//                    ProductViewDialog(this@ListProductActivity,product.id!!,position,
//                        this@ListProductActivity).show(supportFragmentManager, "product")
                }

            })
    }

    private fun initRecyclerCategory() {
        recyclerView_category.adapter = adapterTagList

//        tv_back_category.setOnClickListener {
//            if (!listCategoryForBack.isNullOrEmpty()){
//                val pos = listCategoryForBack.size-1
//                adapterTagList?.updateList(App.database.getAppDao().selectUnderCategory(App.branch(),listCategoryForBack[pos].tag!!.toInt()))
//                if (listCategoryForBack[pos].tag!!.toInt() == 0){
//                    adapterProduct?.updateList(App.database.getAppDao().selectProduct(App.branch()))
//                }else adapterProduct?.updateList(App.database.getAppDao().selectProductByCategory(App.branch(), listCategoryForBack[pos].tag!!.toInt()))
//                listCategoryForBack.removeAt(pos)
//            }
//            initVisibilityIcBack()
//        }
    }

    private fun initRecyclerProduct(){
        recyclerView_product.adapter = adapterProduct
    }

//    override fun onEditProduct(product: Product?, position: Int) {
//        TODO("Not yet implemented")
//    }

    /**
     * Listener
     * */
    override fun onEditProduct( product: Product?, position: Int) {
//        val i = Intent(this@ListProductActivity,ProductActivity::class.java)
//        i.putExtra("product_id", product!!.id)
//        i.putExtra("product_position", position)
//        resultUpdate.launch(i)
        App.toast("hello")
    }
}