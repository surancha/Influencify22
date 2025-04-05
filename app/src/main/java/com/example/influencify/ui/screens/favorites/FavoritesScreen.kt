package com.example.influencify.ui.screens.favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.influencify.data.Ad
import com.example.influencify.data.Favorite
import com.example.influencify.ui.screens.favorites.data.FavoritesScreenObject
import com.example.influencify.ui.screens.login.data.MainScreenDataObject
import com.example.influencify.ui.screens.main.AdListItemUi
import com.example.influencify.ui.screens.main.bottom_menu.BottomMenu
import com.example.influencify.ui.screens.main.getAllAds
import com.example.influencify.ui.screens.main.getAllFavoritesIds
import com.example.influencify.ui.screens.main.onFavorites
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun FavoritesScreen(
    navData: FavoritesScreenObject,
    navController: NavController
) {
    val adsListState = remember { mutableStateOf(emptyList<Ad>()) }
    val db = remember { Firebase.firestore }

    LaunchedEffect(Unit) {
        getAllFavoritesIds(db, navData.uid) { favs ->
            getAllAds(db, favs) { ads ->
                adsListState.value = ads.filter { it.isFavorite }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomMenu(
                navController = navController,
                navData = MainScreenDataObject(uid = navData.uid, email = "")
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(adsListState.value) { ad ->
                AdListItemUi(
                    ad = ad,
                    navController = navController,
                    onFavClick = {
                        adsListState.value = adsListState.value.map {
                            if (it.key == ad.key) {
                                onFavorites(
                                    db,
                                    navData.uid,
                                    Favorite(it.key),
                                    !it.isFavorite
                                )
                                it.copy(isFavorite = !it.isFavorite)
                            } else {
                                it
                            }
                        }
                    }
                )
            }
        }
    }
}