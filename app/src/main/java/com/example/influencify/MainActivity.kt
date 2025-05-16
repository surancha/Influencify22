package com.example.influencify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.influencify.ui.screens.add_ad.AddAdScreen
import com.example.influencify.ui.screens.add_ad.data.AddScreenObject
import com.example.influencify.ui.screens.categories.CategoriesScreen
import com.example.influencify.ui.screens.categories.data.CategoriesScreenObject
import com.example.influencify.ui.screens.login.LoginScreen
import com.example.influencify.ui.screens.login.data.LoginScreenObject
import com.example.influencify.ui.screens.login.data.MainScreenDataObject
import com.example.influencify.ui.screens.main.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = CategoriesScreenObject
            ) {
                composable<LoginScreenObject> {
                    LoginScreen { navData ->
                        navController.navigate(navData)
                    }
                }
                composable<MainScreenDataObject> { navEntry ->
                    MainScreen(
                        navData = navEntry.toRoute(),
                        navController = navController
                    )
                }
                composable<AddScreenObject> {
                    AddAdScreen(
                        navController = navController,
                        navData = navController.previousBackStackEntry?.toRoute()
                            ?: MainScreenDataObject("", "")
                    )
                }
                composable<CategoriesScreenObject> {
                    CategoriesScreen(
                        navController = navController,
                        onCategorieSelected = { category ->
                            println("Selected category: $category")
                        }
                    )
                }
            }
        }
    }
}