package com.example.influencify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.influencify.ui.screens.login.Checker
import com.example.influencify.ui.screens.login.LoginScreen
import com.example.influencify.ui.screens.login.PasswordScreen
import com.example.influencify.ui.screens.main.MainScreen
import com.example.influencify.ui.theme.InfluencifyTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fs = Firebase.firestore
        val auth = Firebase.auth
        setContent {
            Checker()
            LoginScreen()
        }
    }
}