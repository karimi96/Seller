package com.karimi.seller.activity.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.karimi.seller.R
import com.karimi.seller.helper.App
import com.karimi.seller.helper.Config.MONEY_TYPE_DEFAULT
import com.karimi.seller.helper.Session
import com.karimi.seller.widget.text_watcher.PriceTextWatcher
import kotlinx.android.synthetic.main.include_box_setting1.*
import kotlinx.android.synthetic.main.include_box_setting2.*
import kotlinx.android.synthetic.main.toolbar_setting.*

class SettingActivity : AppCompatActivity() {

    val setting = App.database.getAppDao().selectSetting(Session.getInstance().branch)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initData()
        initToolbar()
    }


    private fun initToolbar() {
        arrayOf(title_setting, back_setting).forEach { it.setOnClickListener { onBackPressed() } }

        submit_setting.btn.setOnClickListener {
            submit_setting.showLoader()
            setData()
        }
    }


    private fun initData() {
        initTextWatcherPrice()

        edt_min_order.setText("${setting.min_order}")
        edt_tax.setText("${setting.tax ?: 0}")
        edt_money_type.setText(setting.currency ?: MONEY_TYPE_DEFAULT)
        edt_price_shipping.setText("${setting.shipping_price ?: 0}")
        edt_free_shipping.setText("${setting.shipping_free_on_order ?: 0}")

        checkbox_sound_scanner.isChecked = setting.sound_scanner ?: false
        checkbox_discount.isChecked = setting.cash_discount ?: false
        checkbox_money.isChecked = setting.cash_money ?: false
        checkbox_card.isChecked = setting.cash_card ?: false
        checkbox_debit.isChecked = setting.cash_debit ?: false
    }


    private fun initTextWatcherPrice() {
        edt_min_order?.addTextChangedListener(PriceTextWatcher(edt_min_order) {})
        edt_price_shipping?.addTextChangedListener(PriceTextWatcher(edt_price_shipping) {})
        edt_free_shipping?.addTextChangedListener(PriceTextWatcher(edt_free_shipping) {})
    }


    private fun setData() {
        setting.min_order = App.convertToDouble(edt_min_order)
        setting.tax = App.convertToInt(edt_tax)
        setting.currency = App.getString(edt_money_type)
        setting.shipping_price = App.convertToDouble(edt_price_shipping)
        setting.shipping_free_on_order = App.convertToDouble(edt_free_shipping)
        setting.sound_scanner = checkbox_sound_scanner.isChecked
        setting.cash_discount = checkbox_discount.isChecked
        setting.cash_money = checkbox_money.isChecked
        setting.cash_card = checkbox_card.isChecked
        setting.cash_debit = checkbox_debit.isChecked
        saveData()
    }


    private fun saveData() {
        App.database.getAppDao().insertSetting(setting)

        Session.getInstance().minOrder = setting.min_order
        Session.getInstance().taxPercent = setting.tax ?: 0
        Session.getInstance().moneyType = setting.currency
        Session.getInstance().shippingPrice = setting.shipping_price ?: 0.0
        Session.getInstance().freeShippingPrice = setting.shipping_free_on_order ?: 0.0

        Session.getInstance().setCheckBox(
            setting.sound_scanner ?: false,
            setting.cash_money ?: false, setting.cash_card ?: false,
            setting.cash_debit ?: false, setting.cash_discount ?: false
        )

        Handler().postDelayed({ finish() }, 700)
    }

}