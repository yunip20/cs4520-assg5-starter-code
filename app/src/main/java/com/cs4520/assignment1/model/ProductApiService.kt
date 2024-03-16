package com.cs4520.assignment1.model

import com.cs4520.assignment4.Api
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {
        @GET(Api.ENDPOINT)
        suspend fun getProducts(@Query("page") page: Int?): List<Product>
}