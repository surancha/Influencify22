package com.example.influencify.ui.screens.main.bottom_menu

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.influencify.ui.screens.add_ad.AddAdScreen
import com.example.influencify.ui.screens.add_ad.data.AddScreenObject
import com.example.influencify.ui.screens.favorites.data.FavoritesScreenObject
import com.example.influencify.ui.screens.login.data.MainScreenDataObject
import com.example.influencify.ui.screens.profile.data.ProfileScreenObject

@Composable
fun BottomMenu(navController: NavController, navData: MainScreenDataObject) {
    val menuItems = listOf(
        BottomMenuItem.Home,
        BottomMenuItem.Search,
        BottomMenuItem.Add,
        BottomMenuItem.Favs,
        BottomMenuItem.Profile
    )

    NavigationBar(
        modifier = Modifier.height(80.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val selectedItem = remember { mutableStateOf("Home") }

        menuItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = item.iconId),
                        contentDescription = null,
                        modifier = Modifier.size(27.dp)
                    )
                },
                selected = currentRoute == item.route.toString(),
                onClick = {
                    selectedItem.value = item.title
                    val route = when (item) {
                        is BottomMenuItem.Home -> navData // Navigate to MainScreen
                        is BottomMenuItem.Favs -> FavoritesScreenObject(navData.uid) // Navigate to FavoritesScreen
                        is BottomMenuItem.Add -> AddScreenObject
                        is BottomMenuItem.Profile -> ProfileScreenObject(navData.uid) // Navigate to ProfileScreen
                        else -> item.route // Use the item's route for Search
                    }
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}