package com.fsociety.tictactoe.ui.screens

import android.graphics.Color
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Difficulty{
    EASY,
    MEDIUM,
    HARD
}
enum class FirstPlayer{
    HUMAN,
    PHONE
}

@Composable
fun GameModeScreen(
    onStartGame:(
            difficulty: Difficulty,
            firstPlayer: FirstPlayer
            ) -> Unit
){
    var selectedDifficulty by remember{
        mutableStateOf(Difficulty.MEDIUM)
    }

    var selectedFirstPlayer by remember {
        mutableStateOf(FirstPlayer.HUMAN)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101018))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "VS PHONE",
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(Modifier.height(35.dp))
    }
    
}











