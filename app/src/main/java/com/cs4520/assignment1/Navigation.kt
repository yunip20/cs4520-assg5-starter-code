package com.cs4520.assignment1

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cs4520.assignment1.ui.ProductListScreen
import com.cs4520.assignment1.ui.ProductListViewModel


enum class Screen {
    LOGIN,
    PRODUCTLIST,
}
sealed class NavigationItem(val route: String) {
    object LOGIN : NavigationItem(Screen.LOGIN.name)
    object PRODUCTLIST : NavigationItem(Screen.PRODUCTLIST.name)
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.LOGIN.route,
    viewModel: ProductListViewModel
    ) {
    NavHost( modifier = modifier, navController =  navController, startDestination = startDestination ) {
        composable(NavigationItem.LOGIN.route) {
            LogInScreen(navController)
        }
        composable(NavigationItem.PRODUCTLIST.route) {
            ProductListScreen(viewModel, navController)
//            navController.popBackStack()
        }
    }

} 