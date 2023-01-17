package com.bale.Estudent.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    const val BASE_URL = ""
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).
    addConverterFactory(GsonConverterFactory.create()).build()

    val retrofitService :StudentApiService by lazy {
        retrofit.create(StudentApiService::class.java)
    }
}