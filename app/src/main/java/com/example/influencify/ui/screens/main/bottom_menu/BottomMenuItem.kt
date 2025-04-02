package com.example.influencify.ui.screens.main.bottom_menu

import com.example.influencify.R
import okhttp3.Route
import com.example.influencify.ui.screens.add_ad.data.AddScreenObject

sealed class BottomMenuItem(
    val route: Any, // Changed to Any to support objects like AddScreenObject
    val title: String,
    val iconId: Int
) {
    object Home : BottomMenuItem(
        route = "home", // Placeholder route
        title = "Home",
        iconId = R.drawable.ic_home
    )

    object Search : BottomMenuItem(
        route = "search", // Placeholder route
        title = "Search",
        iconId = R.drawable.ic_search
    )

    object Add : BottomMenuItem(
        route = AddScreenObject, // Use AddScreenObject for navigation
        title = "Add",
        iconId = R.drawable.ic_add
    )

    object Profile : BottomMenuItem(
        route = "profile", // Placeholder route
        title = "Profile",
        iconId = R.drawable.ic_profile
    )

    object Favs : BottomMenuItem(
        route = "favorites", // Placeholder route
        title = "Favorites",
        iconId = R.drawable.ic_favs
    )
}






//
//sealed class BottomMenuItem(
//    val route: String,
//    val title: String,
//    val iconId: Int,
//
//){
//    object Home : BottomMenuItem(
//        route = "",
//        title = "Home",
//        iconId = R.drawable.ic_home
//    )
//
//    object Search : BottomMenuItem(
//            route = "",
//            title = "Search",
//            iconId = R.drawable.ic_search
//    )
//
//    object Add : BottomMenuItem(
//            route = "",
//            title = "Add",
//            iconId = R.drawable.ic_add
//    )
//
//    object Profile : BottomMenuItem(
//            route = "",
//            title = "Profile",
//            iconId = R.drawable.ic_profile
//    )
//
//    object Favs : BottomMenuItem(
//            route = "",
//            title = "Favorites",
//            iconId = R.drawable.ic_favs
//    )
//}
