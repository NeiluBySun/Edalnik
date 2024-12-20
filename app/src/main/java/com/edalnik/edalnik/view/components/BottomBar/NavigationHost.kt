package com.edalnik.edalnik.view.components.BottomBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.edalnik.edalnik.view.screens.DashboardScreen
import com.edalnik.edalnik.view.screens.ProfileScreen
import com.edalnik.edalnik.view.screens.FoodAppendingScreen
import com.edalnik.edalnik.viewmodel.FoodViewModel

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier, viewModel: FoodViewModel) {

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Dashboard.route,
        modifier = modifier
    ) {

        composable(BottomNavItem.Home.route) {
            FoodAppendingScreen(viewModel)
        }
        composable(BottomNavItem.Dashboard.route) {
            DashboardScreen(viewModel)
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen()
        }
    }
}