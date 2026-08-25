package com.fsociety.tictactoe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun GameScreen(){
    var board by remember {
        mutableStateOf(
            List(9){""}
        )
    }

    var currentPlayer by remember {
        mutableStateOf("X")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101018))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "TIC TAC TOE",
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "دور اللاعب :$currentPlayer",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00E5FF)
        )

        Spacer(Modifier.height(35.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (row in 0 .. 2){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (column in 0 .. 2){
                        val index = row * 3 + column
                        GameCell(
                            value = board[index],
                            onClick = {

                                if (board[index].isEmpty()) {

                                    val newBoard = board.toMutableList()

                                    newBoard[index] = currentPlayer

                                    board = newBoard

                                    currentPlayer =
                                        if (currentPlayer == "X") {
                                            "O"
                                        } else {
                                            "X"
                                        }
                                }
                            }
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        Text(
            text = "X  •  O",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFC107)
        )
    }
}

@Composable
private fun GameCell(
    value: String,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .size(95.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF673AB7),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = Color(0xFF252532),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = value,
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (value == "X") {
                Color(0xFF00E5FF)
            } else {
                Color(0xFFFF4081)
            }
        )
    }
}