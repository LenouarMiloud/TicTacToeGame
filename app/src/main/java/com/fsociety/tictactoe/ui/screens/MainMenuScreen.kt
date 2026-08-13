package com.fsociety.tictactoe.ui.screens

import android.R
import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
            color = Color.White
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

        

    }
}












