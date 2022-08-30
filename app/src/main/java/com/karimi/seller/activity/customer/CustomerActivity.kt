package com.karimi.seller.activity.customer

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.karimi.seller.R
import com.karimi.seller.adapter.CustomerListAdapter
import com.karimi.seller.adapter.TagInfoAdapter
import com.karimi.seller.dialog.InsertCustomerDialog
import com.karimi.seller.helper.App
import com.karimi.seller.model.Customers
import com.karimi.seller.model.TagList
import kotlinx.android.synthetic.main.activity_customer.*
import kotlinx.android.synthetic.main.include_toolbar_customer.*
import java.lang.Exception

class CustomerActivity : AppCompatActivity(), InsertCustomerDialog.Listener {

    private var adapter: CustomerListAdapter? = null
    private var adapterTag: TagInfoAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        initToolbar(getString(R.string.customers))
        initAdapterTagList()
        initAdapterCustomer()


    }


    private fun initToolbar(title: String) {
        /* toolbar.title.text = title
         toolbar.ic_back.visibility = View.VISIBLE
         toolbar.ic_back.setOnClickListener { onBackPressed() }
 */


        /* toolbar.ic_search.visibility = View.VISIBLE
         toolbar.ic_search.setOnClickListener {
             toolbar.ic_back.visibility = View.GONE
             toolbar.title.visibility = View.GONE
             toolbar.ic_add.visibility = View.GONE
             toolbar.edt_search.visibility = View.VISIBLE
             toolbar.ic_close.visibility = View.VISIBLE
             toolbar.edt_search.setSelection(0)
         }
         toolbar.ic_close.setOnClickListener {
             toolbar.edt_search.text.clear()
             toolbar.ic_back.visibility = View.VISIBLE
             toolbar.title.visibility = View.VISIBLE
             toolbar.ic_add.visibility = View.VISIBLE
             toolbar.edt_search.visibility = View.GONE
             toolbar.ic_close.visibility = View.GONE
             App.closeKeyboard(this)
         }

         toolbar.edt_search.setOnEditorActionListener { v, actionId, event ->
             when(actionId){
                 EditorInfo.IME_ACTION_DONE,
                 EditorInfo.IME_ACTION_GO,
                 EditorInfo.IME_ACTION_SEARCH->{
                     adapter?.updateList(App.database.getAppDao().searchCustomer(App.branch(), App.getString(toolbar.edt_search)))
                     adapterTag?.removeSelection()
                     App.closeKeyboard(this)
                 }
             }
             return@setOnEditorActionListener false
         }*/



        plus_customer.visibility = View.VISIBLE
        plus_customer.setOnClickListener {
            InsertCustomerDialog(this, null, -1, this)
                .show(supportFragmentManager, "customer")
        }
    }


    override fun insert(dialog: InsertCustomerDialog, customer: Customers, position: Int) {
        customer.id = App.database.getAppDao().insertCustomer(customer).toInt()
        adapter?.addItem(customer, position)
        dialog.dismiss()
    }


    private fun initAdapterTagList() {
        val array_tag = ArrayList<TagList>()
        array_tag.add(TagList("همه", "all"))
        array_tag.add(TagList("فعال", "active"))
        array_tag.add(TagList("بیشترین سفارش", "most_order"))
        array_tag.add(TagList("بدهکاران", "debtor"))
        array_tag.add(TagList("غیرفعال", "inactive"))
        array_tag.add(TagList("کمترین سفارش", "least_order"))

        adapterTag = TagInfoAdapter(this,
            array_tag,
            object : TagInfoAdapter.Listener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClicked(position: Int, item: TagList) {
                    adapter?.updateList(selectCustomer(item.tag!!))
                    App.toast(item.title.toString())
                }
            })

        recycler_tag_customer.adapter = adapterTag
    }


    private fun selectCustomer(query: String): List<Customers> {
        return when (query) {
            "active" -> App.database.getAppDao().selectCustomerByStatus(App.branch(), 1)
            "inactive" -> App.database.getAppDao().selectCustomerByStatus(App.branch(), 0)
            "most_order" -> App.database.getAppDao().selectCustomer(App.branch())
            "least_order" -> App.database.getAppDao().selectCustomer(App.branch())
            "debtor" -> App.database.getAppDao().selectCustomer(App.branch())
            else -> App.database.getAppDao().selectCustomer(App.branch())
        }
    }


    private fun initAdapterCustomer(){
        adapter = CustomerListAdapter(this,
            ArrayList(selectCustomer("all")),
            true,
            object : CustomerListAdapter.Listener {
                override fun onItemClicked(position: Int, item: Customers, action: String?) {
                    when(action){
                        "tel", "sms" -> {
                            try {
                                val i = Intent(Intent.ACTION_VIEW)
                                i.data = Uri.parse("${action}:${item.phone}")
                                startActivity(i)
                            }catch (e: Exception) {
                                App.toast(getString(R.string.your_device_hasnt_this_feathure))
                            }
                        }
                        else -> {
                            var i = Intent(this@CustomerActivity , CustomerViewActivity::class.java)
                            i.putExtra("pos" ,position)
                            i.putExtra("customer_id" ,item.id)
                            startActivity(i)
//                            CustomerViewDialog(this@CustomerActivity,item.id!!,position,this@CustomerActivity)
//                                .show(supportFragmentManager, "customer")
                        }
                    }
                }
            })
        recycler_customer.adapter = adapter
    }







}