package com.example.influencify.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.influencify.data.Ad
import com.example.influencify.data.Favorite
import com.example.influencify.ui.screens.login.data.MainScreenDataObject
import com.example.influencify.ui.screens.main.bottom_menu.BottomMenu
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navData: MainScreenDataObject,
    navController: NavController
) {
    val adsListState = remember { mutableStateOf(emptyList<Ad>()) }
    val filteredAdsListState = remember { mutableStateOf(emptyList<Ad>()) }
    val selectedPlatform = remember { mutableStateOf("All") }
    val searchQuery = remember { mutableStateOf("") }
    val db = remember { Firebase.firestore }

    LaunchedEffect(Unit) {
        getAllFavoritesIds(db, navData.uid) { favs ->
            getAllAds(db, favs) { ads ->
                adsListState.value = ads
                filteredAdsListState.value = ads
            }
        }
    }




    LaunchedEffect(selectedPlatform.value, searchQuery.value) {
        filteredAdsListState.value = adsListState.value.filter { ad ->
            val matchesPlatform = selectedPlatform.value == "All" || ad.platform == selectedPlatform.value
            val matchesSearch = searchQuery.value.isEmpty() ||
                    ad.title.contains(searchQuery.value, ignoreCase = true) ||
                    ad.description.contains(searchQuery.value, ignoreCase = true)
            matchesPlatform && matchesSearch
        }
    }

    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                DrawerHeader(navData.email)
                DrawerBody(
                    onPlatformSelected = { platform ->
                        selectedPlatform.value = platform
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SearchBar(
                    query = searchQuery.value,
                    onQueryChange = { searchQuery.value = it },
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = { Text("Search") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                ) {}
            },
            bottomBar = {
                BottomMenu(navController = navController, navData = navData)
            }
        ) { paddingValues ->
            if (filteredAdsListState.value.isEmpty()) {
                Text(
                    text = if (searchQuery.value.isEmpty()) "No ads available" else "No ads match your search",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(filteredAdsListState.value) { ad ->
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
    }
}
fun getAllAds(
    db: FirebaseFirestore,
    idsList: List<String>,
    onAds: (List<Ad>) -> Unit
) {
    db.collection("ads")
        .get()
        .addOnSuccessListener { task ->
            val adsList = task.toObjects(Ad::class.java).map {
                if (idsList.contains(it.key)) {
                    it.copy(isFavorite = true)
                } else {
                    it
                }
            }
            onAds(adsList)
        }
        .addOnFailureListener {}
}

fun getAllFavoritesIds(
    db: FirebaseFirestore,
    uid: String,
    onFavorites: (List<String>) -> Unit
) {
    db.collection("users")
        .document(uid)
        .collection("favorites")
        .get()
        .addOnSuccessListener { task ->
            val idsList = task.toObjects(Favorite::class.java)
            val keysList = arrayListOf<String>()
            idsList.forEach {
                keysList.add(it.key)
            }
            onFavorites(keysList)
        }
        .addOnFailureListener {}
}

fun onFavorites(
    db: FirebaseFirestore,
    uid: String,
    favorite: Favorite,
    isFav: Boolean,
) {
    if (isFav) {
        db.collection("users")
            .document(uid)
            .collection("favorites")
            .document(favorite.key)
            .set(favorite)
    } else {
        db.collection("users")
            .document(uid)
            .collection("favorites")
            .document(favorite.key)
            .delete()
    }
}