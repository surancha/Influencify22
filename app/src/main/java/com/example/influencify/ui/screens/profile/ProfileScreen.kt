package com.example.influencify.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.influencify.data.Ad
import com.example.influencify.ui.screens.login.data.MainScreenDataObject
import com.example.influencify.ui.screens.main.AdListItemUi
import com.example.influencify.ui.screens.main.bottom_menu.BottomMenu
import com.example.influencify.ui.screens.profile.data.ProfileScreenObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(
    navData: ProfileScreenObject,
    navController: NavController
) {
    val db = remember { Firebase.firestore }
    val userAds = remember { mutableStateOf<List<Ad>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        db.collection("ads")
            .whereEqualTo("creatorUid", navData.uid)
            .get()
            .addOnSuccessListener { task ->
                userAds.value = task.toObjects(Ad::class.java)
                isLoading.value = false
            }
            .addOnFailureListener { exception ->
                errorMessage.value = "Failed to load ads: ${exception.message}"
                isLoading.value = false
            }
    }

    Scaffold(
        bottomBar = {
            BottomMenu(
                navController = navController,
                navData = MainScreenDataObject(navData.uid, "")
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Your Ads",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            when {
                isLoading.value -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                errorMessage.value.isNotEmpty() -> {
                    Text(
                        text = errorMessage.value,
                        color = Color.Red,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                userAds.value.isEmpty() -> {
                    Text(
                        text = "You haven't created any ads yet.",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(userAds.value) { ad ->
                            AdListItemUi(
                                ad = ad,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}