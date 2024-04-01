package com.cs4520.assignment1.model

import androidx.work.WorkManager
import com.cs4520.assignment1.model.api.ProductApiService
import com.cs4520.assignment1.model.api.RetrofitInstance
import com.cs4520.assignment1.model.database.Product
import com.cs4520.assignment1.model.database.ProductDao

class ProductRepository(val dao: ProductDao) {
    private val apiService: ProductApiService = RetrofitInstance.apiService
    suspend fun getProducts(): List<Product> {
        try {
            val response = apiService.getProducts(null)
            return if (response.isSuccessful) {
                val productList =  response.body() ?: emptyList()
                if (productList.isEmpty()){
                    emptyList()
                } else {
                    dao.insertAll(productList)
                    productList
                }
            }  else {
                throw Exception("Error: ${response.body()}")
            }
        } catch (ex: Exception) {
            val fromDatabase = dao.getAllProducts()
            return fromDatabase
        }
    }
}