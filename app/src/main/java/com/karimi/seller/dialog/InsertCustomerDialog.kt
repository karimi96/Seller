package com.karimi.seller.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.karimi.seller.R
import com.karimi.seller.helper.App
import com.karimi.seller.helper.Session
import com.karimi.seller.model.Customers
import kotlinx.android.synthetic.main.dialog_add_customer.*
import java.util.*


class InsertCustomerDialog(val _context: Context, val _customer: Customers?, val _position: Int,
                           val listener: Listener?) : DialogFragment() {

    private var _ID = -1
    private var _POS = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_add_customer, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.isCancelable = true
        initOnClick()
        if (_customer != null) setValue(_customer, _position)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.setGravity(Gravity.CENTER)

        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        dialog!!.window!!.setLayout(((6.7 * width) / 7).toInt(),ViewGroup.LayoutParams.WRAP_CONTENT)


    }

    interface Listener {
        fun insert(dialog: InsertCustomerDialog, customer: Customers, position: Int)
    }

    private fun initOnClick(){
        submit_customer.btn.setOnClickListener {
            if (formIsValid()) listener?.insert(this, getValue(), _POS)
        }
    }

    private fun formIsValid() :Boolean{
        if(App.getString(edt_name_customer).isNullOrEmpty()){
            edt_name_customer.error = _context.resources.getString(R.string.not_valid)
            return false
        }
        return true
    }

     fun setValue(category: Customers, position: Int){
        _POS = position
        if (category.id != null) _ID = category.id!!
        if (!category.name.isNullOrEmpty()) edt_name_customer.setText(category.name)
        if (!category.phone.isNullOrEmpty()) edt_phone.setText(category.phone)
        cb_add_customer.isChecked = category.status != null && category.status == 1
    }

    private fun getValue(): Customers{
        val category = Customers()
        if (_ID != -1) category.id = _ID
        category.name = App.getString(edt_name_customer)
        category.phone = App.getString(edt_phone)
        category.branch = Session.getInstance().branch
        category.status = if(cb_add_customer.isChecked) 1 else 0
        if (category.created_at == null) category.created_at = Date()
        category.updated_at = Date()
        return category
    }
}