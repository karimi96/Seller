package com.karimi.seller.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CheckOutDetail {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id :Int? = null
    var check_out_number :String? = null
    var product_id :Int? = null
    var stock :Double? = null
    var increase_id :Int? = null
    var increase_name :String? = null
    var price_buy :Double? = null
    var price_sale :Double? = null
    var price_discount :Double? = null
    var price_profit :Double? = null
    var price_tax :Double? = null

}