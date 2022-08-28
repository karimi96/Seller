package com.karimi.seller.activity.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karimi.seller.R
import com.karimi.seller.helper.App
import com.karimi.seller.helper.Config.MONEY_TYPE_DEFAULT
import com.karimi.seller.helper.Session
import com.karimi.seller.widget.text_watcher.PriceTextWatcher
import kotlinx.android.synthetic.main.include_box_setting1.*
import kotlinx.android.synthetic.main.include_box_setting2.*

class SettingActivity : AppCompatActivity() {

    val setting = App.database.getAppDao().selectSetting(Session.getInstance().branch)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initData()

    }

    private fun initData(){
        initTextWatcherPrice()

        edt_min_order.setText("${setting.min_order?:0}")
        edt_tax.setText("${setting.tax?:0}")
        edt_money_type.setText(setting.currency?:MONEY_TYPE_DEFAULT)
        edt_price_shipping.setText("${setting.shipping_price?:0}")
        edt_free_shipping.setText("${setting.shipping_free_on_order?:0}")

        checkbox_sound_scanner.isChecked = setting.sound_scanner?:false
        checkbox_discount.isChecked = setting.cash_discount?:false
        checkbox_money.isChecked = setting.cash_money?:false
        checkbox_card.isChecked = setting.cash_card?:false
        checkbox_debit.isChecked = setting.cash_debit?:false
    }


    private fun initTextWatcherPrice(){
        edt_min_order?.addTextChangedListener(PriceTextWatcher(edt_min_order) {})
        edt_price_shipping?.addTextChangedListener(PriceTextWatcher(edt_price_shipping) {})
        edt_free_shipping?.addTextChangedListener(PriceTextWatcher(edt_free_shipping) {})
    }


}