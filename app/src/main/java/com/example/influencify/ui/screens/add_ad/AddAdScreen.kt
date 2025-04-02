package com.example.influencify.ui.screens.add_ad

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.influencify.R
import com.example.influencify.data.Ad
import com.example.influencify.ui.screens.login.LoginButton
import com.example.influencify.ui.screens.login.RoundedCornerTextField
import com.example.influencify.ui.screens.login.data.MainScreenDataObject
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun AddAdScreen(
    navController: NavController, // Add NavController
    navData: MainScreenDataObject // Add MainScreenDataObject to navigate back
) {
    var selectedPlatform = "Instagram"
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val urLink = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val firestore = remember { Firebase.firestore }

    Image(
        painter = painterResource(id = R.drawable.backgraund1),
        contentDescription = "BG",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.4f
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo2),
            contentDescription = "lg",
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = "Add your ad",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        )

        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerDropDownManu { selectedItem ->
            selectedPlatform = selectedItem
        }
        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = title.value,
            label = "Title"
        ) {
            title.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            maxLines = 5,
            singleLine = false,
            text = description.value,
            label = "Description"
        ) {
            description.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = urLink.value,
            label = "URL"
        ) {
            urLink.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = price.value,
            label = "Price"
        ) {
            price.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        LoginButton(
            text = "Save",
            onClick = {
                saveBookToFireStore(
                    firestore,
                    Ad(
                        title = title.value,
                        description = description.value,
                        price = price.value,
                        urLink = urLink.value,
                        platform = selectedPlatform
                    ),
                    onSaved = {
                        // Navigate back to MainScreen with navData
                        navController.navigate(navData) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    },
                    onError = {
                        // Optionally handle error (e.g., show a message)
                    }
                )
            }
        )
    }
}

private fun saveBookToFireStore(
    firestore: FirebaseFirestore,
    ad: Ad,
    onSaved: () -> Unit,
    onError: () -> Unit
) {
    val db = firestore.collection("ads")
    val key = db.document().id
    db.document(key)
        .set(ad.copy(key = key))
        .addOnSuccessListener {
            onSaved()
        }
        .addOnFailureListener {
            onError()
        }
}