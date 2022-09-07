package com.karimi.seller.activity.product

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.karimi.seller.R
import com.karimi.seller.activity.barcodeScanner.BarcodeScannerActivity
import com.karimi.seller.adapter.AutoCompleteAdapter
import com.karimi.seller.adapter.TagAdapter
import com.karimi.seller.dialog.SelectCategoryDialog
import com.karimi.seller.helper.App
import com.karimi.seller.helper.Config
import com.karimi.seller.helper.Session
import com.karimi.seller.model.*
import com.karimi.seller.widget.text_watcher.PriceTextWatcher
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import kotlinx.android.synthetic.main.include_box_add_new_p1.*
import kotlinx.android.synthetic.main.include_box_add_new_p2.*
import kotlinx.android.synthetic.main.include_box_add_new_p3.*
import kotlinx.android.synthetic.main.toolbar_new_p.*
import java.util.*
import kotlin.collections.ArrayList


class AddNewProductActivity : AppCompatActivity(), SelectCategoryDialog.Listener {

    private var _PRODUCT_OBJECT: Product? = null
    private var _POSITION: Int? = null
    private var _IMAGE_DEFULT_PATH = ""
    private var _CATEGORY: ArrayList<Category> = ArrayList()
    private var _DATE_EXPIRED: Date? = null


    private val resultGetBarcodeCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                edt_barcode.setText(result?.data?.extras?.getString(Config.KEY_EXTRA_BARCODE))
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_product)

        initToolbar()
        initExtraDataIntent()
        setValue()
        initActionOnClick()
        initAutoComplete()
        initTextWatcherPrice()
        initDateExpire()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                Glide.with(this).load(resultUri).into(image_new_product)
                fab_new_product.visibility = View.GONE
                _IMAGE_DEFULT_PATH = App.saveFile(App.getByte(resultUri))
//                ic_delete.visibility = View.VISIBLE
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    private fun initToolbar() {
        title_new_product.text =
            if (_PRODUCT_OBJECT?.id != null) "ویرایش ${_PRODUCT_OBJECT?.name}" else "ثبت محصول جدید"
        arrayOf(
            title_new_product,
            back_new_product
        ).forEach { it.setOnClickListener { onBackPressed() } }
        submit_new_product.setOnClickListener { }
    }


    private fun initExtraDataIntent() {
        _PRODUCT_OBJECT = if (intent != null && intent?.extras != null) {
            val extra = intent!!.extras!!.getInt("product_id", -1)
            App.database.getAppDao().selectProduct(App.branch(), extra)
        } else Product()

        _POSITION = if (intent != null && intent?.extras != null) {
            intent!!.extras!!.getInt("product_position", -1)
        } else null
    }


    private fun setValue() {
        if (_PRODUCT_OBJECT?.id != null) {
            if (!_PRODUCT_OBJECT?.image_defult.isNullOrEmpty()) {
                fab_new_product.visibility = View.GONE
                Glide.with(this).load(_PRODUCT_OBJECT!!.image_defult!!).into(image_new_product)
                _IMAGE_DEFULT_PATH = _PRODUCT_OBJECT!!.image_defult!!
//                ic_delete.visibility = View.VISIBLE
                image_new_product.setOnClickListener {
                    CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this)
                }
            }


            edt_tax_percent.setText(
                if (_PRODUCT_OBJECT?.tax_percent == null) "${Session.getInstance().taxPercent}"
                else "${_PRODUCT_OBJECT?.tax_percent!!}"
            )

            if (TextUtils.isEmpty(_PRODUCT_OBJECT?.tax_percent.toString())) {
                checkbox_tax.isChecked = false
                edt_tax_percent.isEnabled = false
            }
//            if (_PRODUCT_OBJECT?.tax_percent == 0){
//                checkbox_tax.isChecked = false
//                edt_tax_percent.isEnabled = false
//            }

            edt_barcode.setText(_PRODUCT_OBJECT?.qrcode ?: "")
            atc_unit.setText(_PRODUCT_OBJECT?.increase ?: "")
            edt_name.setText(_PRODUCT_OBJECT?.name ?: "")
            edt_max_selection.setText((_PRODUCT_OBJECT?.max_selection?.toInt() ?: 0).toString())
            initCategoryProductList()
            edt_price_buy.setText(App.priceFormat(_PRODUCT_OBJECT?.price_buy ?: 0.0))
            edt_price_sela_on_product.setText(
                App.priceFormat(
                    _PRODUCT_OBJECT?.price_sale_on_product ?: 0.0
                )
            )
            edt_price_sela.setText(App.priceFormat(_PRODUCT_OBJECT?.price_sale ?: 0.0))
            edt_stock.setText(App.stockFormat(_PRODUCT_OBJECT?.stock ?: 0.0))
            if (_PRODUCT_OBJECT?.date_expired != null) {
//                tv_add_date_expire.setText(resources.getString(R.string.expired_at,App.getFormattedDate(_PRODUCT_OBJECT?.date_expired)))
                auto_complete_date.setText(App.getFormattedDate(_PRODUCT_OBJECT?.date_expired))
            }
            initTextProfitAndDiscount()
        } else {
//            edt_tax_percent.setText(Session.getInstance().taxPercent.toString())
            edt_tax_percent.setText("")
        }
    }


    private fun initAutoCompleteUnitsList() {
        val spinner = ArrayList<Spinner>()
        val list_unit = App.database.getAppDao().selectUnit(App.branch())
        for (i in list_unit.indices) {
            spinner.add(Spinner(list_unit[i].id!!, list_unit[i].title))
        }
        val adapter = AutoCompleteAdapter(this, spinner, false)
        atc_unit.setAdapter(adapter)
        atc_unit.setOnItemClickListener { parent, view, position, id ->
            val r: Spinner = parent.getItemAtPosition(position) as Spinner
        }
        atc_unit.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) atc_unit.showDropDown()
        }
        atc_unit.setOnClickListener { atc_unit.showDropDown() }
    }

    /* Second Way For AutoComplete*/

    private fun initAutoComplete() {
        val spinner = ArrayList<Spinner>()
        val list_unit = App.database.getAppDao().selectUnit(App.branch())
        for (i in list_unit.indices) {
            spinner.add(Spinner(list_unit[i].id!!, list_unit[i].title))
        }
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_spinner, spinner)
        atc_unit.setAdapter(adapter)
        atc_unit.threshold = 0
    }


    private fun initCategoryProductList() {
        if (_PRODUCT_OBJECT?.id != null) {
            val cat: ArrayList<CategoryProduct> =
                ArrayList(App.database.getAppDao().selectCategoryProduct(_PRODUCT_OBJECT!!.id!!))
            for (i in 0 until cat.size) {
                _CATEGORY.add(
                    App.database.getAppDao().selectCategory(App.branch(), cat[i].id_category!!)
                )
            }
            initCategory()
        }
    }


    private fun initCategory() {
        if (!_CATEGORY.isNullOrEmpty()) {
            tv_add_category.text =
                resources.getString(R.string.this_product_has_n_category, _CATEGORY.size)
            recycler_product_view.visibility = View.VISIBLE
            val array_tag = ArrayList<TagList>()
            for (i in 0 until _CATEGORY.size) {
                array_tag.add(i, TagList(_CATEGORY[i].name, _CATEGORY[i].id.toString()))
            }
            val adapterCategory = TagAdapter(this, array_tag, null)
            recycler_product_view.adapter = adapterCategory
        } else {
            tv_add_category.text = resources.getString(R.string.submit_category)
            recycler_product_view.visibility = View.GONE
        }
    }


    @SuppressLint("SetTextI18n")
    private fun initTextProfitAndDiscount() {
        if (calculateDiscount() > 0) {
            if (tv_discount.visibility != View.VISIBLE) tv_discount.visibility = View.VISIBLE
            tv_discount.text = getString(
                R.string.this_product_has_free,
                App.priceFormat(calculateDiscount(), true)
            )
        } else {
            if (tv_discount.visibility != View.GONE) tv_discount.visibility = View.GONE
        }

        when {
            calculateProfit() > 0 -> {
                if (tv_profit.visibility != View.VISIBLE) tv_profit.visibility = View.VISIBLE
                tv_profit.text = getString(
                    R.string.this_product_has_profit,
                    App.priceFormat(calculateProfit(), true)
                )
//                tv_profit.setTextColor(resources.getColor(R.color.complementary))
            }
            calculateProfit() < 0 -> {
                if (tv_profit.visibility != View.VISIBLE) tv_profit.visibility = View.VISIBLE
                tv_profit.text = getString(
                    R.string.this_product_hast_free,
                    App.priceFormat(calculateProfit(), true)
                )
//                tv_profit.setTextColor(resources.getColor(R.color.red))
            }
            else -> {
                if (tv_profit.visibility != View.GONE) tv_profit.visibility = View.GONE
            }
        }
    }


    private fun calculateDiscount(): Double {
        val _sale_on_product = App.convertToDouble(edt_price_sela_on_product)
        val _sale = App.convertToDouble(edt_price_sela)

        return when {
            _sale_on_product > _sale -> {
                _sale_on_product - _sale
            }
            else -> 0.0
        }
    }


    private fun calculateProfit(): Double {
        val _buy = App.convertToDouble(edt_price_buy)
        val _sale_on_product = App.convertToDouble(edt_price_sela_on_product)
        val _sale = App.convertToDouble(edt_price_sela)

        return when {
            _sale > 0.0 -> {
                _sale - _buy
            }
            _sale_on_product > 0.0 -> {
                _sale_on_product - _buy
            }
            else -> 0.0
        }
    }


    private fun initActionOnClick() {
        textInput_barcode.setEndIconOnClickListener {
            resultGetBarcodeCamera.launch(Intent(this, BarcodeScannerActivity::class.java))
        }

//        btn_scanBarCodeByCamera.setOnClickListener {
//            resultGetBarcodeCamera.launch(Intent(this, BarcodeScannerActivity::class.java))
//        }

        fab_new_product.setOnClickListener {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this)
        }

//        ic_delete.setOnClickListener {
//            _IMAGE_DEFULT_PATH = ""
//            image.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add_photo_alternate_black_24dp))
//            ic_delete.visibility = View.GONE
//        }


        checkbox_tax.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                if (isChecked) {
                    text_input_percent.isHintAnimationEnabled = true
                    text_input_percent.isHintEnabled = true
                    edt_tax_percent.isEnabled = true
                    edt_tax_percent.setText("")
//                    edt_tax_percent.setText(Session.getInstance().taxPercent.toString())
                } else {
                    edt_tax_percent.setText("")
                    text_input_percent.isHintAnimationEnabled = false
                    text_input_percent.isHintEnabled = false
                    edt_tax_percent.isEnabled = false
//                    edt_tax_percent.setText("0")
                }
            }
        }

        tv_add_category.setOnClickListener {
            SelectCategoryDialog(this, _CATEGORY, this@AddNewProductActivity).show(
                supportFragmentManager,
                "category"
            )
        }

//        tv_add_date_expire.setOnClickListener {
//            tv_add_date_expire.visibility = View.GONE
//            box_date_expire.visibility = View.VISIBLE
//        }

        submit_new_product.btn.setOnClickListener {
            if (formIsValid()) {
                submit_new_product.showLoader()
                val idProduct = App.database.getAppDao().insertProduct(getValue()).toInt()
                insertUnitList()
                insertCategoryProductList(idProduct)
                Handler().postDelayed({ submitted(idProduct) }, 500)
            }
        }
    }


    private fun formIsValid(): Boolean {
        getValue()
        var value_is_true = "true"

        if (_PRODUCT_OBJECT?.name.isNullOrEmpty()) {
            edt_name.error = resources.getString(R.string.not_valid)
            value_is_true = "false"
        }

        if (_PRODUCT_OBJECT?.increase.isNullOrEmpty()) {
            atc_unit.error = resources.getString(R.string.not_valid)
            value_is_true = "false"
        }

        if (_PRODUCT_OBJECT?.branch == null) {
        }

        if (_PRODUCT_OBJECT?.status == null) {
        }

        if (_PRODUCT_OBJECT?.stock == null) {
            edt_stock.setError(resources.getString(R.string.not_valid))
            value_is_true = "false"
        }

        if (_PRODUCT_OBJECT?.tax_percent == null) {
        }

        if (_PRODUCT_OBJECT?.user == null) {
        }

        return value_is_true == "true"
    }


    private fun initTextWatcherPrice() {
        edt_price_buy.addTextChangedListener(PriceTextWatcher(edt_price_buy) { initTextProfitAndDiscount() })
        edt_price_sela_on_product?.addTextChangedListener(PriceTextWatcher(edt_price_sela_on_product) { initTextProfitAndDiscount() })
        edt_price_sela.addTextChangedListener(PriceTextWatcher(edt_price_sela) { initTextProfitAndDiscount() })
    }

    private fun getValue(): Product {
        _PRODUCT_OBJECT?.id = if (_PRODUCT_OBJECT?.id != null) _PRODUCT_OBJECT?.id!! else null
        _PRODUCT_OBJECT?.image_defult = _IMAGE_DEFULT_PATH
        _PRODUCT_OBJECT?.date_expired = _DATE_EXPIRED
        _PRODUCT_OBJECT?.branch = Session.getInstance().branch
        _PRODUCT_OBJECT?.user = Session.getInstance().user
        _PRODUCT_OBJECT?.qrcode = App.getString(edt_barcode)
        _PRODUCT_OBJECT?.name = App.getString(edt_name)
        _PRODUCT_OBJECT?.descrption = null
        _PRODUCT_OBJECT?.increase = App.getString(atc_unit)
        _PRODUCT_OBJECT?.stock = App.convertToDouble(edt_stock)
        _PRODUCT_OBJECT?.price_buy = App.convertToDouble(edt_price_buy)
        _PRODUCT_OBJECT?.price_sale_on_product = App.convertToDouble(edt_price_sela_on_product)
        _PRODUCT_OBJECT?.price_sale =
            if (App.convertToDouble(edt_price_sela) == 0.0) _PRODUCT_OBJECT?.price_sale_on_product!!
            else App.convertToDouble(edt_price_sela)
        _PRODUCT_OBJECT?.price_discount = calculateDiscount()
        _PRODUCT_OBJECT?.price_profit = calculateProfit()
        _PRODUCT_OBJECT?.max_selection =
            App.convertToDouble(edt_max_selection)// must get value of edittext
        _PRODUCT_OBJECT?.min_selection = 1.0

        _PRODUCT_OBJECT?.tax_percent =
            if (App.convertToInt(edt_tax_percent) == Session.getInstance().taxPercent && checkbox_tax.isChecked) {
                null
            } else App.convertToInt(edt_tax_percent)

        _PRODUCT_OBJECT?.status = 1
        if (_PRODUCT_OBJECT?.id != null) {
            _PRODUCT_OBJECT?.updated_at = Date()
        } else {
            _PRODUCT_OBJECT?.created_at = Date()
            _PRODUCT_OBJECT?.updated_at = Date()
        }
        return _PRODUCT_OBJECT!!
    }


    private fun insertUnitList() {
        if (App.database.getAppDao().selectUnit(App.branch(), App.getString(atc_unit)) == null) {
            App.database.getAppDao().insertUnit(UnitModel(App.getString(atc_unit), App.branch()))
        }
    }

    private fun insertCategoryProductList(idProduct: Int) {
        App.database.getAppDao().deleteCategoryProduct(idProduct)

        val categoryProductList: ArrayList<CategoryProduct> = ArrayList()
        for (i in 0 until _CATEGORY.size) {
            categoryProductList.add(CategoryProduct(idProduct, _CATEGORY[i].id))
        }
        App.database.getAppDao().insertCategoryProduct(categoryProductList)
    }


    private fun initDateExpire() {
        /*val array_tag = ArrayList<TagList>()
        array_tag.add(TagList("بدون انقضا","0"))
        array_tag.add(TagList("۷ روز دیگر","7"))
        array_tag.add(TagList("۱۵ روز دیگر","15"))
        array_tag.add(TagList("یکماه دیگر","30"))
        array_tag.add(TagList("سه ماه دیگر","90"))
        array_tag.add(TagList("شش ماه دیگر","180"))
        array_tag.add(TagList("یکسال دیگر","365"))
        array_tag.add(TagList("دوسال دیگر","730"))*/
        val array_tag = ArrayList<String>()
        array_tag.add("بدون انقضا")
        array_tag.add("۷ روز دیگر")
        array_tag.add("۱۵ روز دیگر")
        array_tag.add("یکماه دیگر")
        array_tag.add("سه ماه دیگر")
        array_tag.add("شش ماه دیگر")
        array_tag.add("یکسال دیگر")
        array_tag.add("دوسال دیگر")

        var adapter = ArrayAdapter(this,  R.layout.simple_list_item_spinner, array_tag)
        auto_complete_date.setAdapter(adapter)
        auto_complete_date.threshold = 0


        /* val adapterTagList = TagAdapter(this,
             array_tag,
             object : TagAdapter.Listener {
                 @SuppressLint("NotifyDataSetChanged")
                 override fun onItemClicked(position: Int, item: TagList) {
                     if (item.tag.equals("0")){
                         _DATE_EXPIRED = null
 //                        tv_add_date_expire.setText(resources.getString(R.string.add_date_expire))
                         auto_complete_date.text.clear()
 //                        tv_add_date_expire.visibility = View.VISIBLE
 //                        box_date_expire.visibility = View.GONE
                     }else{
                         val calendar = Calendar.getInstance()
                         calendar.time = Date()
                         calendar.add(Calendar.DAY_OF_YEAR, +item.tag!!.toInt())
                         _DATE_EXPIRED = calendar.time
                         auto_complete_date.setText(App.getFormattedDate(_DATE_EXPIRED!!))
                     }
                 }
             })*/
//        recyclerView_date.adapter = adapterTagList

        btn_open_calender.setOnClickListener {
            val picker = PersianDatePickerDialog(this)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMinYear(PersianDatePickerDialog.THIS_YEAR)
                .setActionTextColor(Color.GRAY)
                .setTypeFace(Typeface.createFromAsset(assets, "font/iran_sans_fanum.ttf"))
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(object : PersianPickerListener {
                    override fun onDateSelected(persianPickerDate: PersianPickerDate) {
                        auto_complete_date.setText(App.getFormattedDate(persianPickerDate.timestamp))
//                        adapterTagList.updateItemSelected(-1)
                    }

                    override fun onDismissed() {}
                })
            picker.show()
        }
    }


    private fun submitted(idProduct: Int) {
        val i = Intent()
        i.putExtra("product_id", idProduct)
        if (_POSITION != null) i.putExtra("product_position", _POSITION)
        setResult(RESULT_OK, i)
        finish()
    }

    override fun onSubmit(dialog: SelectCategoryDialog, list: ArrayList<Category>?) {
        initCategory()
        dialog.dismiss()
    }

}