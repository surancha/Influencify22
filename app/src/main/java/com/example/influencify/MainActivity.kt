package com.example.influencify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.influencify.ui.screens.add_ad.AddAdScreen
import com.example.influencify.ui.screens.add_ad.data.AddScreenObject
import com.example.influencify.ui.screens.login.LoginScreen
import com.example.influencify.ui.screens.login.SignUpScreen
import com.example.influencify.ui.screens.login.data.LoginScreenObject
import com.example.influencify.ui.screens.login.data.MainScreenDataObject
import com.example.influencify.ui.screens.login.data.SignUpScreenObject
import com.example.influencify.ui.screens.main.MainScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fs = Firebase.firestore
        val auth = Firebase.auth
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = LoginScreenObject
            ) {
                composable<LoginScreenObject> {
                    LoginScreen { navData ->
                        navController.navigate(navData)
                    }
                }
                composable<MainScreenDataObject> { navEntry ->
                    val navData = navEntry.toRoute<MainScreenDataObject>()
                    MainScreen(
                        navData = navData,
                        navController = navController
                    )
                }
                composable<AddScreenObject> {
                    // Get the previous MainScreenDataObject from the back stack
                    val navData = navController.previousBackStackEntry
                        ?.toRoute<MainScreenDataObject>()
                        ?: MainScreenDataObject("", "") // Fallback (shouldn't happen in normal flow)
                    AddAdScreen(
                        navController = navController,
                        navData = navData
                    )
                }
                composable<SignUpScreenObject> {
                    SignUpScreen(
                        onBackToLogin = { navController.navigate(LoginScreenObject) },
                        onNavigateToMainScreen = { navData -> navController.navigate(navData) }
                    )
                }
            }
        }
    }
}


//package com.example.influencify
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.runtime.Composable
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.toRoute
//import com.example.influencify.ui.screens.add_ad.AddAdScreen
//import com.example.influencify.ui.screens.add_ad.data.AddScreenObject
//import com.example.influencify.ui.screens.login.Checker
//import com.example.influencify.ui.screens.login.LoginScreen
//import com.example.influencify.ui.screens.login.SignUpScreen
//import com.example.influencify.ui.screens.login.data.LoginScreenObject
//import com.example.influencify.ui.screens.login.data.MainScreenDataObject
//import com.example.influencify.ui.screens.login.data.SignUpScreenObject
//import com.example.influencify.ui.screens.main.MainScreen
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val fs = Firebase.firestore
//        val auth = Firebase.auth
//        setContent {
//            val navController = rememberNavController()
//            NavHost(
//                navController = navController,
//                startDestination = LoginScreenObject
//            ) {
//                composable<LoginScreenObject> {
//                    LoginScreen { navData ->
//                        navController.navigate(navData)
//                    }
//                }
//                composable<MainScreenDataObject> { navEntry ->
//                    val navData = navEntry.toRoute<MainScreenDataObject>()
//                    MainScreen(navData)
//                }
//                composable<AddScreenObject> {
//                    AddAdScreen()
//                }
//
//                composable<SignUpScreenObject> {
//                    SignUpScreen(
//                        onBackToLogin = { navController.navigate(LoginScreenObject) },
//                        onNavigateToMainScreen = { navData -> navController.navigate(navData) }
//                    )
//                }
//            }
//        }
//    }
//}