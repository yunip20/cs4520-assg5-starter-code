package com.cs4520.assignment1.model

class ProductRepository(private val apiService: ProductApiService) {
    suspend fun getProducts(page: Int?): List<Product> {
        return apiService.getProducts(page)
    }

}