package com.example.influencify.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.influencify.data.Ad
import com.example.influencify.ui.screens.login.data.MainScreenDataObject
import com.example.influencify.ui.screens.main.bottom_menu.BottomMenu
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdDetailScreen(
    adKey: String,
    navController: NavController
) {
    var ad by remember { mutableStateOf<Ad?>(null) }
    val db = Firebase.firestore

    // Fetch the ad from Firestore when the screen loads
    LaunchedEffect(adKey) {
        db.collection("ads")
            .document(adKey)
            .get()
            .addOnSuccessListener { document ->
                ad = document.toObject(Ad::class.java)
            }
            .addOnFailureListener {
                // Handle error (e.g., show a toast or log)
            }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    if (ad != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                            text = ad!!.title,
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp
                            )
                        }

                    }

                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomMenu(navController = navController, navData = MainScreenDataObject(uid = "", email = ""))
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (ad != null) {
                // Display the ad photo
                AsyncImage(
                    model = ad!!.imageUrl,
                    contentDescription = "Ad Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Display the title
                Text(
                    text = ad!!.title,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Display the platform
                Text(
                    text = ad!!.platform,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Display the description
                Text(
                    text = ad!!.description,
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                    maxLines = 10
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Display the price
                Text(
                    text = "${ad!!.price}$",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Display the URL
                Text(
                    text = ad!!.urLink,
                    color = Color.Blue,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Display favorite status
                Text(
                    text = "Favorite: ${if (ad!!.isFavorite) "Yes" else "No"}",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            } else {
                Text(
                    text = "Loading...",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }
        }
    }
}