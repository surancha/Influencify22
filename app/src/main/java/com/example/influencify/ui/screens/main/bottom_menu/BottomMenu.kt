package com.example.influencify.ui.screens.main.bottom_menu

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomMenu(navController: NavController) {
    val menuItems = listOf(
        BottomMenuItem.Home,
        BottomMenuItem.Search,
        BottomMenuItem.Add,
        BottomMenuItem.Profile,
        BottomMenuItem.Favs
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        menuItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.iconId), contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route.toString(), // Convert route to string for comparison
                onClick = {
                    navController.navigate(item.route) {
                        // Avoid stacking multiple instances of the same screen
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






//package com.example.influencify.ui.screens.main.bottom_menu
//
//import androidx.compose.foundation.layout.height
//import androidx.compose.material3.Icon
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun BottomMenu() {
//    val items = listOf(
//        BottomMenuItem.Home,
//        BottomMenuItem.Search,
//        BottomMenuItem.Add,
//        BottomMenuItem.Favs,
//        BottomMenuItem.Profile
//    )
//
//    val selectedItem = remember { mutableStateOf("Home") }
//
//    NavigationBar {
//        items.forEach { item ->
//            NavigationBarItem(
//                selected = selectedItem.value == item.title,
//                onClick = {
//                    selectedItem.value = item.title
//                },
//                icon = {
//                    Icon(
//                        painterResource(id = item.iconId),
//                        contentDescription = null,
//                        modifier = Modifier.height(50.dp),
//                    )
//                },
////                label = {
////                    Text(text = item.title)
////                }
//            )
//        }
//    }
//}