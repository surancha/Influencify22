package com.example.influencify.ui.screens.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.influencify.R
import com.example.influencify.ui.screens.login.data.MainScreenDataObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun Checker() {
    val auth = remember { Firebase.auth }
    Log.d("My Log", "User email ${auth.currentUser?.email}")
}

@Composable
fun LoginScreen(
    onNavigateToMainScreen: (MainScreenDataObject) -> Unit
) {
    val textTypography1 = Typography(
        bodyLarge = TextStyle(

            fontWeight = FontWeight.Normal,
            fontSize = 25.sp,
            color = Color.Gray
        )
    )
    var e = ""
    val auth = Firebase.auth
    val fs = Firebase.firestore
    val errorState = remember {
        mutableStateOf("")
    }
    val emailState = remember {
        mutableStateOf("stepanyanruben31@gmail.com")
    }
    val passwordState = remember {
        mutableStateOf("1234567890")
    }
    Image(
        painter = painterResource(
            id = R.drawable.backgraund1
        ),
        contentDescription = "BG",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 25.dp, end = 25.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.logo2
            ), contentDescription = "lg"
        )
        Spacer(modifier = Modifier.height(40.dp))
        RoundedCornerTextField(
            text = emailState.value,
            label = "Email",
        ) {
            emailState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = passwordState.value,
            label = "Password",
        ) {
            passwordState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (errorState.value.isNotEmpty()) {
            Text(
                text = errorState.value,
                color = Color.Red,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(10.dp))


        LoginButton(
            text = "Sign In",
            onClick = {
                signIn(
                    auth,
                    emailState.value,
                    passwordState.value,
                    onSignInSuccess = { navData ->
                        onNavigateToMainScreen(navData)

                        Log.d("My Log", "Sign In Successful!")
                    },
                    onSignInFailure = { error ->
                        Log.d("My Log", "Sign In Failure!: $error")
                        errorState.value = error
                    }
                )

            })
        Text(
            text = "_______or______",
            style = textTypography1.bodyLarge
        )
        Spacer(modifier = Modifier.height(10.dp))

        LoginButton(
            text = "Sign Up",
            onClick = {
                signUp(
                    auth,
                    emailState.value,
                    passwordState.value,
                    onSignUpSuccess = {navData ->
                        onNavigateToMainScreen(navData)
                        e = emailState.value
                        Log.d("My Log", "Sign Up Successful!")
                    },
                    onSignUpFailure = { error ->
                        Log.d("My Log", "Sign Up Failure!: $error")
                        errorState.value = error
                    }
                )

            })


    }
}


fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignUpSuccess: (MainScreenDataObject) -> Unit,
    onSignUpFailure: (String) -> Unit
) {
    if (email.isBlank() || password.isBlank()) {
        onSignUpFailure("Email and Password cannot be empty")
        return
    }
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignUpSuccess(
                    MainScreenDataObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!,
                    )
                )
            }
        }
        .addOnFailureListener {
            onSignUpFailure(it.message ?: "Sign up error")
        }

}

fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSuccess: (MainScreenDataObject) -> Unit,
    onSignInFailure: (String) -> Unit
) {
    if (email.isBlank() || password.isBlank()) {
        onSignInFailure("Email and Password cannot be empty")
        return
    }
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignInSuccess(
                    MainScreenDataObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!,
                    )
                )
            }
        }
        .addOnFailureListener {
            onSignInFailure(it.message ?: "Sign up error")
        }

}