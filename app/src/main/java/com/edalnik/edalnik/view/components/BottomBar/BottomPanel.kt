package com.edalnik.edalnik.view.components.BottomBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.edalnik.edalnik.R

sealed class BottomNavItem(val icon: Int, val route: String) {
    object Profile : BottomNavItem(R.drawable.profile, "profile")
    object Home : BottomNavItem(R.drawable.cutlery, "home")
    object Dashboard : BottomNavItem(R.drawable.dashboard, "dashboard")
}



@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Profile,
        BottomNavItem.Home,
        BottomNavItem.Dashboard
    )

    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(bottom = 18.dp),
            horizontalArrangement = Arrangement.Center
    ) {
        NavigationBar(
            modifier = Modifier
                .height(76.dp)
                .width(290.dp)
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(45.dp)),
                containerColor = Color.Gray,
            ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = item.icon), contentDescription = null) },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}