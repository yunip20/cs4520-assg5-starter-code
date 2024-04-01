package com.cs4520.assignment1.ui

// ProductListViewModel.kt
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cs4520.assignment1.model.database.Product
import com.cs4520.assignment1.model.database.ProductDatabase
import com.cs4520.assignment1.model.ProductRepository
import com.cs4520.assignment1.model.worker.ProductWorkManager
import com.cs4520.assignment5.data.worker.UpdateProductListWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class ProductListViewModel() : ViewModel() {

    private val repo : ProductRepository;

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val wm: WorkManager
    init{
        wm = ProductWorkManager.worker
        val productDao = ProductDatabase.getInstance().productDao()
        repo = ProductRepository(productDao)
    }

    fun loadProducts() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                schedulePeriodicWork(wm)
                val products = withContext(Dispatchers.IO) {repo.getProducts() }
                _products.value = products
            }
            catch(e: Exception) {
                _errorMessage.value = e.toString()
            }
            finally {
                _isLoading.value = false
            }
        }
    }

    private val lifecycleOwnerLiveData: MutableLiveData<LifecycleOwner> = MutableLiveData()

//    fun doSomethingWithLifecycleOwner(wm: WorkManager) {
//        // Observe the lifecycleOwnerLiveData
//        lifecycleOwnerLiveData.observeForever {
//            // Do something with the lifecycle owner
//            wm.getWorkInfosForUniqueWorkLiveData("productListWorker")
//                .observe(lifecycleOwner){ listOfWorkStatuses ->
//                    val workStatus = listOfWorkStatuses.get(0)
//                    val finished: Boolean = workStatus.getState().isFinished()
//                    Log.d("STATUSes", listOfWorkStatuses.size.toString())
//                    if (!finished) {
//                        Log.d("WIP", "YHEST")
//                    } else {
//                    }
//                }
//            // For example, you can observe lifecycle events or perform other actions
//        }
//    }

    // Method to fetch products
    fun updateProducts() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repo.getProducts().also { _products.value = it } // Update products LiveData
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    private fun schedulePeriodicWork(wm: WorkManager) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<UpdateProductListWorker>(
            repeatInterval = 1, // Repeat every hour
            repeatIntervalTimeUnit = TimeUnit.HOURS
        ).setInitialDelay(30, TimeUnit.HOURS).setConstraints(constraints).build()

        runCatching {
            wm.enqueueUniquePeriodicWork(
                "productListWorker",
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                periodicWorkRequest
            ).getResult()
        }.onSuccess {
            Log.d("WorkManager", "Periodic work enqueued successfully.")
//            doSomethingWithLifecycleOwner(wm)
        }.onFailure { error ->
            Log.e("WorkManager", "Error enqueuing periodic work: $error")
//            doSomethingWithLifecycleOwner(wm)
        }
    }
}
