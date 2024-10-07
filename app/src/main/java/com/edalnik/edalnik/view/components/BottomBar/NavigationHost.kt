package com.edalnik.edalnik.view.components.BottomBar
import HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.edalnik.edalnik.view.screens.DashboardScreen
import com.edalnik.edalnik.view.screens.ProfileScreen

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route, modifier = modifier) {
        composable(BottomNavItem.Home.route) {
            HomeScreen()  // Экран "Домой"
        }
        composable(BottomNavItem.Dashboard.route) {
            DashboardScreen()  // Экран "Поиск"
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen()  // Экран "Профиль"
        }
    }
}