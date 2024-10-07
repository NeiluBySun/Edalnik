import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.edalnik.edalnik.view.components.BottomBar.BottomNavigationBar
import com.edalnik.edalnik.view.components.BottomBar.NavigationHost


@Composable
fun MainScreen() {
    // Контроллер навигации для управления переходами между экранами
    val navController = rememberNavController()

    Scaffold(
        // Нижняя панель навигации
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        // Навигационный хост для отображения экранов в зависимости от текущего маршрута
        NavigationHost(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}