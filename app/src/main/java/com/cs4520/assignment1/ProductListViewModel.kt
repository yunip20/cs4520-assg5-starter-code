package com.cs4520.assignment1

// ProductListViewModel.kt
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs4520.assignment1.model.Product
import com.cs4520.assignment1.model.ProductDatabase
import com.cs4520.assignment1.model.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListViewModel(private val repository: ProductRepository, private val db: ProductDatabase) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _pageLoadedEvent = MutableLiveData<Int>()
    val pageLoadedEvent: LiveData<Int> = _pageLoadedEvent

    private val productDao = db.productDao()
    private var currentPage = 1

    init{
        loadNextPage()
    }
    private suspend fun fetchProductsAndHandleError(page: Int?, onSuccess: (List<Product>) -> Unit) {
        try {
            val productList = repository.getProducts(page)
            onSuccess(productList)
        } catch (e: Exception) {
            handleErrorAndLoadOfflineData(e)
        }
    }

    private fun handleErrorAndLoadOfflineData(e: Exception) {
        _errorMessage.value = "Error loading products: ${e.message}"
        viewModelScope.launch {
            _isLoading.value = true
            val databaseProducts = withContext(Dispatchers.IO) {
                productDao.getAllProducts()
            }
            _products.value = databaseProducts
            if (databaseProducts.isNotEmpty()) {
                // Set the retrieved data from the database to _products
                _products.value = databaseProducts
            } else {
                _errorMessage.value = "No products available offline"
            }
        }
    }
    fun loadProducts(page: Int?) {
        _isLoading.value = true
        viewModelScope.launch {
            fetchProductsAndHandleError(page)  {productList ->
                _products.value = productList
                productList.map { product ->
                    viewModelScope.launch { productDao.insertProduct(product) }
                }
                if (productList.isEmpty()) {
                    _errorMessage.value = "No products available"
                }
            }
            _isLoading.value = false
        }
    }

    fun loadNextPage() {
        _isLoading.value = true
        viewModelScope.launch {
            fetchProductsAndHandleError(currentPage) { productList ->
                if (productList.isNotEmpty()) {
                    _pageLoadedEvent.value = currentPage
                    _products.value = _products.value.orEmpty() + productList
                    productList.map { product ->
                        viewModelScope.launch { productDao.insertProduct(product)}
                    }
                    currentPage++
                } else {
                    _errorMessage.value = "No more products available"
                }
            }
            _isLoading.value = false
        }
    }

}
