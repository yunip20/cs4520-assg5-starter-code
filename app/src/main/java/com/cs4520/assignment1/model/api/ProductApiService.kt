package com.cs4520.assignment1.model.api

import com.cs4520.assignment1.model.database.Product
import com.cs4520.assignment4.Api
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {
        @GET(Api.ENDPOINT)
        suspend fun getProducts(@Query("page") page: Int?): Response<List<Product>>
}