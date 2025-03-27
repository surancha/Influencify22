package com.example.influencify.ui.screens.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.influencify.R


@Composable
fun LoginButton(

    text: String,
    onClick: () -> Unit
) {
    Button(onClick = {
        onClick()

    },
        modifier = Modifier.fillMaxWidth().height(55.dp) ,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        )


        ) {
        Text(text = text,
//            fontFamily = interFont,
            fontWeight = FontWeight.Bold,)
    }

}