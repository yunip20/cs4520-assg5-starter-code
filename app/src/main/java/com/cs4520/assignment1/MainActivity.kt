package com.cs4520.assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private fun convertDataSet(dataset: List<List<Any?>>) : List<ProductItem.Product> {
        val result = dataset.map { item ->
            val name = item.get(0)?.toString() ?: ""
            val type = item.get(1)?.toString() ?: ""
            val expiryDate = item.get(2)?.toString() ?: ""
            val price = item.get(3)?.toString() ?: ""

            ProductItem.Product(name, expiryDate, price, type)
        }
        return result
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.rvProducts)as RecyclerView
        val adapter = ProductAdapter(convertDataSet(productsDataset))
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_graph)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_graph)
        if(navController.currentDestination?.id == R.id.loginFragment){
            finish() // close the app
        }
        if (!navController.popBackStack()) {
            super.onBackPressed()
        }
    }
}