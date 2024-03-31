package com.cs4520.assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.cs4520.assignment4.R
import com.cs4520.assignment4.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onBackPressed() {
        // Use the NavController to handle back navigation
        if (!navController.navigateUp()) {
            // If navigateUp() returns false, there are no more destinations in the back stack
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
    }

}