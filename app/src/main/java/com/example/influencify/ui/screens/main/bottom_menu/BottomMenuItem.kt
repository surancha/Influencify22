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
import com.example.influencify.R
import com.example.influencify.ui.screens.add_ad.data.AddScreenObject
import com.example.influencify.ui.screens.favorites.data.FavoritesScreenObject

sealed class BottomMenuItem(
    val route: Any,
    val title: String,
    val iconId: Int
) {
    object Home : BottomMenuItem(
        route = "home",
        title = "Home",
        iconId = R.drawable.ic_home
    )

    object Search : BottomMenuItem(
        route = "search",
        title = "Search",
        iconId = R.drawable.ic_search
    )

    object Add : BottomMenuItem(
        route = AddScreenObject,
        title = "Add",
        iconId = R.drawable.ic_add
    )

    object Profile : BottomMenuItem(
        route = "profile",
        title = "Profile",
        iconId = R.drawable.ic_profile
    )

    object Favs : BottomMenuItem(
        route = "favorites", // Temporary placeholder
        title = "Favorites",
        iconId = R.drawable.ic_favs
    )
}

@Composable
fun BottomMenu(navController: NavController, uid: String) {
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
                        is BottomMenuItem.Favs -> FavoritesScreenObject(uid) // Use uid to create route
                        else -> item.route
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