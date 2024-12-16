package com.edalnik.edalnik.view.components.BottomBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
    object Dashboard : BottomNavItem(R.drawable.dashboard, "dashboard")
    object Home : BottomNavItem(R.drawable.cutlery, "home")
}



@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Profile,
        BottomNavItem.Dashboard,
        BottomNavItem.Home
    )

    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(Color(0xFF121212)),
            horizontalArrangement = Arrangement.Center
    ) {
        NavigationBar(
            modifier = Modifier
                .height(100.dp)
                .width(250.dp)
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(50.dp))
                .border(
                    BorderStroke(1.dp, Color(0xFF7E7E7E)),
                    shape = RoundedCornerShape(45.dp)
                ),
                containerColor = Color(0xFF393737),
                contentColor = Color(0xFF514F4F)

            ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                            )

                           },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFFCD74),
                        unselectedIconColor = Color(0xFF0E0E0E),
                        indicatorColor = Color(0xFF514F4F)
                    ),
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(3.dp)
                        .height(65.dp)
                )
            }
        }
    }
}