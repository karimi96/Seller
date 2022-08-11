package com.karimi.seller.activity.business_add.mvp

import com.karimi.seller.model.ResponseBusinessSample


interface FirstOpenView {
    fun onResponse(arrayListBusinessSample: ArrayList<ResponseBusinessSample.item>?)
    fun onResponseSampleData(sampleData: ResponseBusinessSample.SampleData)
    fun onError(message: String)
    fun onEmpty()
    fun stopResponse()
}