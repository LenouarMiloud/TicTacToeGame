package com.fsociety.tictactoe.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuScreen(
    onPlayerVsPlayerClick : () -> Unit,
    onPlayerVsPhoneClick : () -> Unit
){
    val backgroundColor = Color(0xFF101018)

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "TIC",
            fontSize = 52.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )

        Text(
            text = "TAC TOE",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00E5FF)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "إختر طريقة اللعب",
            fontSize = 18.sp,
            color = Color.LightGray
        )

        Spacer(Modifier.height(45.dp))

        Button(
            onClick = onPlayerVsPlayerClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF673AB7))
        ) {
            Text(
                text = "\uD83D\uDC64  لاعب ضد لاعب  \uD83D\uDC64",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onPlayerVsPlayerClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF009688))
        ){
            Text(
                text = "\uD83D\uDC64  لاعب ضد الهاتف  \uD83E\uDD16",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(45.dp))

        Text(
            text = "X . O",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFC107)
        )
    }
}







