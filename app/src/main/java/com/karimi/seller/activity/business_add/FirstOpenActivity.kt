package com.karimi.seller.activity.business_add

import android.app.Dialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.method.LinkMovementMethod
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.karimi.seller.R
import com.karimi.seller.activity.business_add.mvp.FirstOpenModel
import com.karimi.seller.activity.business_add.mvp.FirstOpenView
import com.karimi.seller.activity.main.MainActivity
import com.karimi.seller.helper.App
import com.karimi.seller.helper.Session
import com.karimi.seller.model.*
import kotlinx.android.synthetic.main.activity_first_open.*
import kotlinx.android.synthetic.main.box_first_activity.*
import java.util.*

class FirstOpenActivity : AppCompatActivity() , FirstOpenView {
    private var branch = App.branch()
//    private var adapter: BusinessAdapter? = null
    private var presenter: FirstOpenModel? = null
    private var URL_SAMPLE_DATA : String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_open)

        var typeface = Typeface.createFromAsset(assets,"font/iran_sans.ttf")
        description.typeface = typeface


        presenter = FirstOpenModel(this)
        presenter?.getBusinessSample()

        initOnClick()
        edt_name.requestFocus()
        initBoxSpinner()

//        initShowOnlyFirst()
//        parent_view.animate().alpha(1f).duration = 500
//        initRecyclerView()
    }


    private fun initOnClick(){
        checkbox.movementMethod = LinkMovementMethod.getInstance()

        submit.btn.setOnClickListener {
            submit.showLoader()
            if (formIsValid()){
                val id_business : Long = App.database.getAppDao().insertBusiness(
                    Business(
                        App.getString(edt_name),
                        App.getString(edt_business_name),
                        "",
                        "1",
                        Date(),
                        Date()
                    )
                )
                branch = id_business.toInt()
                App.database.getAppDao().insertSetting(Setting(id_business.toInt()))
                Session.getInstance().setBusiness(
                    App.getString(edt_name),
                    App.getString(edt_business_name),
                    id_business.toInt()
                )
                Handler().postDelayed({ getSampleData() },300)
            }else submit.hideLoader()
        }

//        exit.setOnClickListener { onBackPressed() }
    }

   /* private fun initShowOnlyFirst(){
        box_only_first.visibility = if (Session.getInstance().sessionKey.isNullOrEmpty()) View.VISIBLE else View.GONE
        exit.visibility = if (Session.getInstance().sessionKey.isNullOrEmpty()) View.GONE else View.VISIBLE
    }*/



//    private fun initRecyclerView(){
//        adapter = BusinessAdapter(this, ArrayList(),this)
//        recyclerView.adapter = adapter
//    }

    private fun formIsValid() : Boolean{
        if (App.getString(edt_name).isNullOrEmpty()){
            edt_name.error = resources.getString(R.string.not_valid)
            return false
        }
        if (App.getString(edt_business_name).isNullOrEmpty()){
            edt_business_name.error = resources.getString(R.string.not_valid)
            return false
        }
        if (!checkbox.isChecked){
            checkbox.error = resources.getString(R.string.not_valid)
            return false
        }
        return true
    }

    private fun getSampleData(){
        if (URL_SAMPLE_DATA!= null){
            presenter?.getBusinessSample(URL_SAMPLE_DATA!!)
        }else{
            next()
        }
    }

    private fun next(){
        if (Session.getInstance().sessionKey.isNullOrEmpty()){
            Session.getInstance().sessionKey = Date().toString()
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            App.toast(resources.getString(R.string.add_business_successfully))
        }
        finish()
    }

    /**
     * Object Insert by convert
     * */

    private fun insertProduct(pl : ArrayList<ResponseBusinessSample.Product>){
        val l = ArrayList<Product>()
        for (i in 0 until pl.size){
            val p = pl[i];
            l.add(Product(p.id_code,p.qrCode,p.title,p.image,branch,p.stock,p.price_buy,p.price_on_product,p.price_sale,p.unit))
        }
        App.database.getAppDao().insertProduct(l)
    }

    private fun insertCategory(cl : ArrayList<ResponseBusinessSample.Category>){
        val l = ArrayList<Category>()
        for (i in 0 until cl.size){
            val c = cl[i]
            l.add(Category(0,c.id_code,c.name,c.image,branch))
        }
        App.database.getAppDao().insertCategory(l)
        return
    }

    private fun insertCategoryProduct(cp : ArrayList<ResponseBusinessSample.ProductCategory>){
        val l = ArrayList<CategoryProduct>()
        for (i in 0 until cp.size){
            val p = App.database.getAppDao().selectProductByIdCode(branch,cp[i].id_code_product!!)
            val c = App.database.getAppDao().selectCategoryByIdCode(branch,cp[i].id_code_category!!)
            l.add(CategoryProduct(p.id,c.id))
        }
        App.database.getAppDao().insertCategoryProduct(l)
    }

    private fun insertUnit(string: ArrayList<String>){
        val l = ArrayList<UnitModel>()
        for (i in 0 until string.size) l.add(UnitModel(string[i],branch))
        App.database.getAppDao().insertUnit(l)
    }

    /**
     * Listener
     * */
//    override fun onItemClicked(position: Int, item: ResponseBusinessSample.item) {
//        URL_SAMPLE_DATA = item.data_url
//    }






    override fun onResponse(arrayListBusinessSample: ArrayList<ResponseBusinessSample.item>?) {
//        if (!arrayListBusinessSample.isNullOrEmpty()){
//            adapter?.addItem(arrayListBusinessSample)
//        }else{
//            adapter?.addItemDefault()
//        }
    }

    override fun onResponseSampleData(sampleData: ResponseBusinessSample.SampleData) {
        if (!sampleData.product.isNullOrEmpty()){
            insertProduct(sampleData.product)
            insertCategory(sampleData.category!!)
            insertCategoryProduct(sampleData.product_category!!)
            insertUnit(sampleData.unit!!)
        }
        Handler().postDelayed({next()},2500)
    }

    override fun onError(message: String) {
        submit.hideLoader()
//        adapter?.addItemDefault()
//        statuser.onError(object : Statuser.Listener{
//            override fun onTryAgain() {
//                presenter?.getBusinessSample()
//            }
//        })
    }

    override fun onEmpty() {

    }

    override fun stopResponse() {
//        statuser.onFinish()
    }


    private fun initBoxSpinner() {
        val reason = resources.getStringArray(R.array.ReasonOfCancel)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, reason)
        auto_complete_txt.setAdapter(adapter)
        auto_complete_txt.threshold = 0
    }
}