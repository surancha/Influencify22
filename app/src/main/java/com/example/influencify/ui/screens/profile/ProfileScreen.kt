package com.example.influencify.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.influencify.ui.screens.main.bottom_menu.BottomMenu
import com.example.influencify.ui.screens.login.data.MainScreenDataObject
import com.example.influencify.ui.screens.profile.data.ProfileScreenObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import coil3.compose.AsyncImage

data class ProfileData(
    val name: String = "",
    val surname: String = "",
    val nickname: String = "",
    val bio: String = "",
    val photoUrl: String = ""
)

@Composable
fun ProfileScreen(
    navData: ProfileScreenObject,
    navController: NavController
) {
    val db = remember { Firebase.firestore }
    val nameState = remember { mutableStateOf("") }
    val surnameState = remember { mutableStateOf("") }
    val nicknameState = remember { mutableStateOf("") }
    val bioState = remember { mutableStateOf("") }
    val photoUrlState = remember { mutableStateOf("") }
    val isSaving = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    
    // Fetch profile data on first load
    LaunchedEffect(Unit) {
        fetchProfileData(db, navData.uid) { profile ->
            nameState.value = profile.name
            surnameState.value = profile.surname
            nicknameState.value = profile.nickname
            bioState.value = profile.bio
            photoUrlState.value = profile.photoUrl
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
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Your Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Profile Photo
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable {
                        // In a real app, this would launch image picker
                        photoUrlState.value = "https://example.com/placeholder.jpg"
                    },
                contentAlignment = Alignment.Center
            ) {
                if (photoUrlState.value.isNotEmpty()) {
                    AsyncImage(
                        model = photoUrlState.value,
                        contentDescription = "Profile Photo",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Photo",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Name Field
            OutlinedTextField(
                value = nameState.value,
                onValueChange = { nameState.value = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Surname Field
            OutlinedTextField(
                value = surnameState.value,
                onValueChange = { surnameState.value = it },
                label = { Text("Surname") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Nickname Field
            OutlinedTextField(
                value = nicknameState.value,
                onValueChange = { nicknameState.value = it },
                label = { Text("Nickname") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Bio Field
            OutlinedTextField(
                value = bioState.value,
                onValueChange = { bioState.value = it },
                label = { Text("Bio") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Save Button
            Button(
                onClick = {
                    isSaving.value = true
                    saveProfileData(
                        db = db,
                        uid = navData.uid,
                        profile = ProfileData(
                            name = nameState.value,
                            surname = surnameState.value,
                            nickname = nicknameState.value,
                            bio = bioState.value,
                            photoUrl = photoUrlState.value
                        )
                    ) {
                        isSaving.value = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isSaving.value) "Saving..." else "Save Profile")
            }
        }
    }
}

fun fetchProfileData(
    db: com.google.firebase.firestore.FirebaseFirestore,
    uid: String,
    onProfileData: (ProfileData) -> Unit
) {
    db.collection("users")
        .document(uid)
        .collection("data")
        .document("profile")
        .get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val profile = document.toObject(ProfileData::class.java)
                profile?.let { onProfileData(it) }
            } else {
                onProfileData(ProfileData())
            }
        }
        .addOnFailureListener {
            onProfileData(ProfileData())
        }
}

fun saveProfileData(
    db: com.google.firebase.firestore.FirebaseFirestore,
    uid: String,
    profile: ProfileData,
    onComplete: () -> Unit
) {
    db.collection("users")
        .document(uid)
        .collection("data")
        .document("profile")
        .set(profile)
        .addOnSuccessListener {
            onComplete()
        }
        .addOnFailureListener {
            onComplete()
        }
} 