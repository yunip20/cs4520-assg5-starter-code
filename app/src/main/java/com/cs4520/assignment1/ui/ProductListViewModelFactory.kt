package com.cs4520.assignment5

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cs4520.assignment1.ui.ProductListViewModel


class ProductListViewModelFactory() : ViewModelProvider.AndroidViewModelFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)){
            return ProductListViewModel() as T
        }
        throw IllegalArgumentException("Creating ViewModel failed")
    }
}