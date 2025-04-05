package com.example.influencify.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.influencify.data.Ad
import com.example.influencify.ui.screens.login.data.MainScreenDataObject
import com.example.influencify.ui.screens.main.bottom_menu.BottomMenu
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun MainScreen(
    navData: MainScreenDataObject,
    navController: NavController
) {
    val adsListState = remember {
        mutableStateOf(emptyList<Ad>())
    }
    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        getAllAds(db){ads ->  
            adsListState.value = ads
        }
    }
    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                DrawerHeader(navData.email)
                DrawerBody()
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomMenu(navController = navController) // Pass NavController
            }
        ) { paddingVales->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingVales)
            ) {
                items(adsListState.value){ ad->
                    AdListItemUi(
                        ad,
                        onFavClick = {
                            adsListState.value = adsListState.value.map {
                                if (it.key == ad.key){
                                    it.copy(isFavorite = !it.isFavorite)
                                }else {
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
private fun getAllAds(
    db: FirebaseFirestore,
    onAds: (List<Ad>) -> Unit){
    db.collection("ads")
        .get()
        .addOnSuccessListener { task->
            val adsList = task.toObjects(Ad::class.java)
            onAds(adsList)
        }
        .addOnFailureListener {

        }
}





//package com.example.influencify.ui.screens.main
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material3.ModalNavigationDrawer
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import com.example.influencify.ui.screens.login.data.MainScreenDataObject
//import com.example.influencify.ui.screens.main.bottom_menu.BottomMenu
//
//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun MainScreen(navData: MainScreenDataObject){
//    ModalNavigationDrawer(
//        modifier = Modifier.fillMaxWidth(),
//        drawerContent = {
//            Column(
//                modifier = Modifier.fillMaxWidth(0.7f)
//            ) {
//                DrawerHeader(navData.email)
//                DrawerBody()
//            }
//
//
//        }
//    ) {
//        Scaffold(
//            modifier = Modifier.fillMaxSize(),
//            bottomBar = {
//                BottomMenu()
//            }
//        ) {
//
//        }
//    }
//
//}