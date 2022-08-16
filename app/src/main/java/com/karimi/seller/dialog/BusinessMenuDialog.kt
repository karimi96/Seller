package com.karimi.seller.dialog

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.karimi.seller.R
import com.karimi.seller.activity.business_add.FirstOpenActivity
import com.karimi.seller.adapter.BusinessListAdapter
import com.karimi.seller.helper.App
import com.karimi.seller.helper.Session
import com.karimi.seller.model.Business
import kotlinx.android.synthetic.main.activity_first_open.submit
import kotlinx.android.synthetic.main.dialog_menu_business.*
import kotlinx.android.synthetic.main.item_menu_dialog_business_edit.*
import kotlinx.android.synthetic.main.item_menu_dialog_business_edit.view.*
import kotlinx.android.synthetic.main.item_menu_dialog_business_main.view.*
import java.util.*

class BusinessMenuDialog(val _context: Context, val listener: Listener?) : DialogFragment() {

    private var business = App.database.getAppDao().selectBusiness(Session.getInstance().businessID)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_menu_business, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.isCancelable = true

        initBusinessData()
        initOnClick()
        initRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.setGravity(Gravity.TOP or Gravity.START)
    }

    interface Listener{
        fun onEditBusiness(dialog: BusinessMenuDialog, business: Business?)
        fun onBusinessList(dialog: BusinessMenuDialog, business: Business)
    }

    private fun initBusinessData(){
        box_edit.edt_owner.setText(business?.owner_name)
        box_edit.edt_business_name.setText(business?.business_name)
    }

    private fun initOnClick(){
        box_main.tv_add_business.setOnClickListener {
            startActivity(Intent(context, FirstOpenActivity::class.java))
            dismiss()
        }

        box_main.tv_edit_business.setOnClickListener {
            box_main.animate().alpha(0f).duration = 200
            Handler().postDelayed({
                box_main.visibility = View.GONE
                box_edit.visibility = View.VISIBLE
            },200)
            Handler().postDelayed({box_edit.animate().alpha(1f).duration = 300},200)
        }

        box_edit.submit.btn.setOnClickListener {
            if (business!=null){
                if (formIsValid()){
                    isCancelable = false
                    submit.showLoader()
                    App.database.getAppDao().insertBusiness(getBusinessValue())
                    Session.getInstance().setBusiness(getBusinessValue())
                    listener?.onEditBusiness(this,getBusinessValue())
                    dismiss()
                }
            }
        }
    }

    private fun initRecyclerView(){
        val arrayList = ArrayList<Business>(App.database.getAppDao().selectBusinessExcept(Session.getInstance().businessID))
        box_main.recyclerView.adapter = BusinessListAdapter(_context,arrayList,object : BusinessListAdapter.Listener{
            override fun onEmpty(size: Int) {
                box_main.box_business.visibility = if (size == 0)  View.GONE else View.VISIBLE
            }

            override fun onItemClicked(position: Int, item: Business) {
                Session.getInstance().setBusiness(
                    item.owner_name,
                    item.business_name,
                    item.id!!
                )
                Session.getInstance().sessionKey = item.session_key
                listener?.onBusinessList(this@BusinessMenuDialog,item)
                dismiss()
            }
        })
    }

    private fun formIsValid() :Boolean{
        if(App.getString(box_edit.edt_owner).isNullOrEmpty()){
            box_edit.edt_owner.error = context?.resources?.getString(R.string.not_valid)
            return false
        }
        if(App.getString(edt_business_name).isNullOrEmpty()){
            edt_business_name.error = context?.resources?.getString(R.string.not_valid)
            return false
        }
        return true
    }

    private fun getBusinessValue(): Business{
        business?.owner_name = App.getString(box_edit.edt_owner)
        business?.business_name = App.getString(edt_business_name)
        business?.updated_at = Date()
        return business!!
    }
}