package com.example.influencify.ui.screens.add_ad

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.influencify.R
import com.example.influencify.ui.screens.login.LoginButton
import com.example.influencify.ui.screens.login.RoundedCornerTextField


@Composable
fun AddAdScreen() {
    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
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

        Text(text = "Add new book",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = FontFamily.Serif)

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = title.value,
            label = "Email",
        ) {
            title.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = description.value,
            label = "Password",
        ) {
            description.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        LoginButton(
            text = "Select Image",
            onClick = {

            })

        Spacer(modifier = Modifier.height(10.dp))

        LoginButton(
            text = "Save",
            onClick = {


            })


    }
}