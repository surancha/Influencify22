package com.example.influencify.ui.screens.main.bottom_menu

import com.example.influencify.R
import okhttp3.Route

sealed class BottomMenuItem(
    val route: String,
    val title: String,
    val iconId: Int,

){
    object Home : BottomMenuItem(
        route = "",
        title = "Home",
        iconId = R.drawable.ic_home
    )

    object Search : BottomMenuItem(
            route = "",
            title = "Search",
            iconId = R.drawable.ic_search
    )

    object Profile : BottomMenuItem(
            route = "",
            title = "Profile",
            iconId = R.drawable.ic_profile
    )

    object Favs : BottomMenuItem(
            route = "",
            title = "Favorites",
            iconId = R.drawable.ic_favs
    )
}
