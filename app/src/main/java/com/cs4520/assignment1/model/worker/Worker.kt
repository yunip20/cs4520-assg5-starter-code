package com.cs4520.assignment5.data.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.cs4520.assignment1.model.ProductRepository
import com.cs4520.assignment1.model.api.RetrofitInstance.apiService
import com.cs4520.assignment1.model.database.ProductDatabase
import java.time.LocalDateTime
import java.util.Calendar

class UpdateProductListWorker(context: Context, params: WorkerParameters):
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result  {
        return try {
            val currentTime = Calendar.getInstance().time
            Log.d("doWork", "Timestamp: $currentTime")
            val dao = ProductDatabase.getInstance().productDao()
            val repo = ProductRepository(dao)
            try {
                val products = repo.getProducts()
                return if (products.isEmpty()) {
                    Result.success()
                } else {
                    dao.insertAll(products)
                    Result.success()
                }
            } catch (e: Exception) {
                Log.e("API error", e.message!!)
                Result.failure()
            }
        } catch (e: Throwable) {
            Log.e("ProductListWorker", e.message!!)
            Result.failure()
        }
    }

}