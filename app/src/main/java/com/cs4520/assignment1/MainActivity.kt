package com.cs4520.assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.databinding.ActivityMainBinding
import com.cs4520.assignment1.productsDataset

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private fun convertDataSet(dataset: List<List<Any?>>) : List<Product> {
        val result = dataset.map { item ->
            val name = item.getOrNull(0)?.toString() ?: ""
            val expiryDate = item.getOrNull(1)?.toString()
            val price = item.getOrNull(2)?.toString() ?: ""
            val type = item.getOrNull(3)?.toString() ?: ""

            Product(name, expiryDate, price, type)
        }
        return result
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = ProductAdapter(convertDataSet(productsDataset))
        recyclerView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        if (navController.currentDestination?.id == R.id.loginFragment) {
            finish() // Close the app
        }
        super.onBackPressed() // Default behavior (navigate back)
    }
}