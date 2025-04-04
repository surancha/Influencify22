package com.example.influencify.ui.screens.main.bottom_menu

import androidx.compose.foundation.layout.height
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
import com.example.influencify.ui.screens.favorites.data.FavoritesScreenObject
import com.example.influencify.ui.screens.login.data.MainScreenDataObject

@Composable
fun BottomMenu(navController: NavController, navData: MainScreenDataObject) {
    val menuItems = listOf(
        BottomMenuItem.Home,
        BottomMenuItem.Search,
        BottomMenuItem.Add,
        BottomMenuItem.Favs,
        BottomMenuItem.Profile
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val selectedItem = remember { mutableStateOf("Home") }

        menuItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = item.iconId),
                        contentDescription = null,
                        modifier = Modifier.height(50.dp)
                    )
                },
                selected = currentRoute == item.route.toString(),
                onClick = {
                    selectedItem.value = item.title
                    val route = when (item) {
                        is BottomMenuItem.Home -> navData // Navigate to MainScreen
                        is BottomMenuItem.Favs -> FavoritesScreenObject(navData.uid) // Navigate to FavoritesScreen
                        else -> item.route // Use the item's route for Add, Search, Profile
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