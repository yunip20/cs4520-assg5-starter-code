package com.cs4520.assignment1.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cs4520.assignment1.model.ProductItem
import com.cs4520.assignment1.model.database.Product

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    navController: NavController
){
    val products by viewModel.products.observeAsState(emptyList())
    val isloading by viewModel.isLoading.observeAsState(true)
    val error by viewModel.errorMessage.observeAsState("")
    // API Call
    LaunchedEffect(true){
        viewModel.loadProducts()
    }
    Surface(
        color = MaterialTheme.colors.background
    ) {
        BackHandler(
            // your condition to enable handler
            enabled = true
        ) {
            // your action to be called if back handler is enabled
            navController.popBackStack()
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment =  Alignment.CenterHorizontally
        ) {
            if (isloading) {
                LinearProgressIndicator(
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth().height(10.dp),
                )
            } else {
                if(error.isNotEmpty()) {
                   Text(error)
                } else {
                    ProductList(products)
                }
            }
        }
    }
}

@Composable
fun ProductList(
    pl: List<Product>
) {
    if (pl.isEmpty()) {
        Text(
            text="No Products Available",
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentSize()
        )

    } else {
        LazyColumn(Modifier.fillMaxHeight()){
            items(pl) {
                ProductItem(it)
            }
        }
    }
}
