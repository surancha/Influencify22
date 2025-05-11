package com.example.influencify.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.influencify.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Preview(showBackground = true)
@Composable
fun PasswordScreen(){
    val textTypography1 = Typography(
        bodyLarge = TextStyle(

            fontWeight = FontWeight.Normal,
            fontSize = 25.sp,
            color = Color.Gray
        )
    )
    val auth = Firebase.auth
    val fs = Firebase.firestore
    val passwordState = remember {
        mutableStateOf("")
    }
    val passwordState1 = remember {
        mutableStateOf("")
    }
    Image(
        painter = painterResource(
            id = R.drawable.backgraund
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
//        Image(painter = painterResource(
//            id = R.drawable.logo2
//        ), contentDescription = "lg")
        Spacer(modifier = Modifier.height(40.dp))
        RoundedCornerTextField(
            text = passwordState.value,
            label = "Password",
        ) {
            passwordState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = passwordState1.value,
            label = "Confirm Password",
        ) {
            passwordState1.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        LoginButton(
            text = "Sign Up",
            onClick = {
//                signUp(auth, passwordState.value, passwordState.value)

            })
        Spacer(modifier = Modifier.height(10.dp))
//        Text(
//            text = "_______or______",
//            style = textTypography1.bodyLarge
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//
//
//        LoginButton(
//            text = "Sign In",
//            onClick = {
////                signIn(auth, passwordState.value, passwordState.value)
//
//            })
//

    }
}