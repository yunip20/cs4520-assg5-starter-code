package com.cs4520.assignment1.model

import com.cs4520.assignment4.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Api.BASE_URL) // Base URL for your API
            .addConverterFactory(GsonConverterFactory.create()) // JSON converter
            .build()
    }
    val apiService: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }
}