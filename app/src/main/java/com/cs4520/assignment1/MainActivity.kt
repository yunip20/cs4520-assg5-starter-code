package com.cs4520.assignment1

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cs4520.assignment1.model.database.ProductDatabase
import com.cs4520.assignment1.model.worker.ProductWorkManager
import com.cs4520.assignment1.ui.ProductListViewModel
import com.cs4520.assignment5.ProductListViewModelFactory
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var viewModel: ProductListViewModel
        ProductWorkManager.worker = WorkManager.getInstance(this)
        ProductDatabase.setContext(this)
        viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)
        val viewModelFactory = ProductListViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductListViewModel::class.java)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavHost(viewModel = viewModel, navController = rememberNavController())
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
    }

}