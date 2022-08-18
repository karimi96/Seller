package com.karimi.seller.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.karimi.seller.R
import kotlinx.android.synthetic.main.widget_statuser.view.*

class StatUser (context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.widget_statuser, this)
    }

    interface Listener{
        fun onTryAgain()
    }

    fun onProgress(){
        progress.visibility = View.VISIBLE
        box_empty.visibility = View.GONE
        box_error.visibility = View.GONE
    }

    fun onEmpty(){
        box_empty.visibility = View.VISIBLE
        box_error.visibility = View.GONE
    }

    fun onError(){
        box_error.visibility = View.VISIBLE
        box_empty.visibility = View.GONE
    }

    fun onError(listener: Listener){
        onError()
        box_error.btn_try.setOnClickListener {
            onProgress()
            listener.onTryAgain()
        }
    }

    fun onFinish(){
        box_empty.visibility = View.GONE
        box_error.visibility = View.GONE
        progress.visibility = View.GONE
    }

}