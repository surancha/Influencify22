package com.example.influencify.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.influencify.ui.screens.login.data.MainScreenDataObject
import com.example.influencify.ui.screens.main.bottom_menu.BottomMenu

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navData: MainScreenDataObject,
    navController: NavController // Add NavController parameter
) {
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
        ) {
            // Add content here if needed (e.g., Home screen by default)
        }
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