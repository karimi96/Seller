package com.karimi.seller.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Increase {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id :Int? = null
    var name :String? = null
    var increase :Double? = null
}