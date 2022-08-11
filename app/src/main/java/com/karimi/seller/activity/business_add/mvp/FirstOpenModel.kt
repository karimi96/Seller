package com.karimi.seller.activity.business_add.mvp

import amingoli.com.selar.activity.business_add.mvp.FirstOpenPresenter
import com.karimi.seller.api.ApiClient
import com.karimi.seller.model.ResponseBusinessSample
import com.karimi.seller.model.ResponseData
import retrofit2.Call
import retrofit2.Response

class FirstOpenModel(val view: FirstOpenView) : FirstOpenPresenter {
    override fun getBusinessSample() {
        ApiClient.getClient().getBusinessSample()
            .enqueue(object : retrofit2.Callback<ResponseData<ResponseBusinessSample>> {
                override fun onFailure(call: Call<ResponseData<ResponseBusinessSample>>, t: Throwable) {
                    view.stopResponse()
                    view.onError(t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseData<ResponseBusinessSample>>,
                    response: Response<ResponseData<ResponseBusinessSample>>) {
                    view.stopResponse()
                    if (response.isSuccessful) {
                        view.onResponse(response.body()?.data?.business)
                    } else {
                        view.onError("getData")
                    }
                }
            })
    }

    override fun getBusinessSample(url: String) {
        ApiClient.getClient().getBusinessSample(url)
            .enqueue(object : retrofit2.Callback<ResponseData<ResponseBusinessSample>> {
                override fun onFailure(call: Call<ResponseData<ResponseBusinessSample>>, t: Throwable) {
                    view.stopResponse()
                    view.onError(t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseData<ResponseBusinessSample>>,
                    response: Response<ResponseData<ResponseBusinessSample>>) {
                    view.stopResponse()
                    if (response.isSuccessful) {
                        if (response.body()?.data?.sample_data != null){
                            view.onResponseSampleData(response.body()?.data?.sample_data!!)
                        }else{
                            view.onEmpty()
                        }
                    } else {
                        view.onError("getData")
                    }
                }
            })
    }
}