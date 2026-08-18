package com.fsociety.tictactoe.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

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
    
}