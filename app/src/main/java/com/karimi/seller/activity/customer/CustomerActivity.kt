package com.karimi.seller.activity.customer

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
import kotlinx.android.synthetic.main.include_toolbar_customer.*

class CustomerActivity : AppCompatActivity(), InsertCustomerDialog.Listener {

    private var adapter: CustomerListAdapter? = null
    private var adapterTag: TagInfoAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        initToolbar(getString(R.string.customers))
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

}