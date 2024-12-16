import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.edalnik.edalnik.model.EdalnikModel
import com.edalnik.edalnik.view.components.BottomBar.BottomNavigationBar
import com.edalnik.edalnik.view.components.BottomBar.NavigationHost
import com.edalnik.edalnik.viewmodel.FoodViewModel


@Composable
fun MainScreen(viewModel: FoodViewModel) {
    val navController = rememberNavController()


    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavigationHost(navController = navController, modifier = Modifier.padding(innerPadding), viewModel)
    }
}