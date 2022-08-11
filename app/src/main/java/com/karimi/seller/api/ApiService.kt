package com.karimi.seller.api

import com.karimi.seller.model.ResponseBusinessSample
import com.karimi.seller.model.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("business_sample.json")
    fun getBusinessSample(): Call<ResponseData<ResponseBusinessSample>>

    @GET("{url}")
    fun getBusinessSample(@Path("url") url: String ): Call<ResponseData<ResponseBusinessSample>>
}