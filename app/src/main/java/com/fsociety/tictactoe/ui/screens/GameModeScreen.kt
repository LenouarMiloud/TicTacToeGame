package com.fsociety.tictactoe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

        Text(
            text = "إختر مستوى الصعــوبة",
            color = Color.LightGray,
            fontSize = 19.sp
        )

        Spacer(Modifier.height(20.dp))

        DifficultyButton(
            text = "🟢 سهل",
            selected = selectedDifficulty == Difficulty.EASY,
            onClick = {
                selectedDifficulty = Difficulty.EASY
            }
        )
        Spacer(
            modifier = Modifier.height(12.dp)
        )

        DifficultyButton(
            text = "🟡 عادي",
            selected = selectedDifficulty == Difficulty.MEDIUM,
            onClick = {
                selectedDifficulty = Difficulty.MEDIUM
            }
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        DifficultyButton(
            text = "🔴 صعب",
            selected = selectedDifficulty == Difficulty.HARD,
            onClick = {
                selectedDifficulty = Difficulty.HARD
            }
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        Text(
            text = "من يبدأ؟",
            color = Color.LightGray,
            fontSize = 19.sp
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            PlayerButton(
                text = "👤 أنا",
                selected = selectedFirstPlayer == FirstPlayer.HUMAN,
                modifier = Modifier.weight(1f),
                onClick = {
                    selectedFirstPlayer = FirstPlayer.HUMAN
                }
            )

            PlayerButton(
                text = "🤖 الهاتف",
                selected = selectedFirstPlayer == FirstPlayer.PHONE,
                modifier = Modifier.weight(1f),
                onClick = {
                    selectedFirstPlayer = FirstPlayer.PHONE
                }
            )
        }

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        Button(
            onClick = {
                onStartGame(
                    selectedDifficulty,
                    selectedFirstPlayer
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00A896)
            )
        ) {

            Text(
                text = "ابدأ اللعبة 🎮",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun DifficultyButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) {
                Color(0xFF673AB7)
            } else {
                Color(0xFF252532)
            }
        )
    ) {

        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun PlayerButton(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = modifier.height(55.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) {
                Color(0xFF673AB7)
            } else {
                Color(0xFF252532)
            }
        )
    ) {

        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}









