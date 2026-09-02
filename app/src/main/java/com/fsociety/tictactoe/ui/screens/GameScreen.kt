package com.fsociety.tictactoe.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fsociety.tictactoe.viewmodel.GameViewModel

@Composable
fun GameScreen(
    gameType: GameType,
    difficulty: Difficulty,
    firstPlayer: FirstPlayer,
    onBackToMenu: () -> Unit,
    gameViewModel: GameViewModel = viewModel()
) {

    val board by gameViewModel.board.collectAsState()

    val currentPlayer by gameViewModel.currentPlayer.collectAsState()

    val winner by gameViewModel.winner.collectAsState()

    val winningLine by gameViewModel.winningLine.collectAsState()

    val isDraw by gameViewModel.isDraw.collectAsState()

    val isPhoneThinking by gameViewModel.isPhoneThinking.collectAsState()

    val humanScore by gameViewModel.humanScore.collectAsState()

    val phoneScore by gameViewModel.phoneScore.collectAsState()

    val drawScore by gameViewModel.drawScore.collectAsState()

    LaunchedEffect(gameType,difficulty, firstPlayer) {

        gameViewModel.setupGame(
            gameType = gameType,
            difficulty = difficulty,
            firstPlayer = firstPlayer
        )
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

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "👤 أنت",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = humanScore.toString(),
                    color = Color(0xFF00E5FF),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "🤝",
                    color = Color.White,
                    fontSize = 18.sp
                )

                Text(
                    text = drawScore.toString(),
                    color = Color(0xFFFFC107),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "🤖 الهاتف",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = phoneScore.toString(),
                    color = Color(0xFFFF4081),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Text(
            text = when {

                winner != null ->
                    "🏆 الفائز: $winner"

                isDraw ->
                    "🤝 تعادل!"

                isPhoneThinking ->
                    "🤖 الهاتف يفكر..."

                else ->
                    "👤 دورك ($currentPlayer)"
            },

            color = Color(0xFF00E5FF),

            fontSize = 20.sp,

            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        Box(
            contentAlignment = Alignment.Center
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                for (row in 0..2) {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        for (column in 0..2) {

                            val index = row * 3 + column

                            GameCell(
                                value = board[index],
                                isWinningCell = index in winningLine,
                                onClick = {
                                    gameViewModel.makeMove(index)
                                }
                            )
                        }
                    }
                }
            }

            if (winningLine.isNotEmpty()) {

                Canvas(
                    modifier = Modifier.size(301.dp)
                ) {

                    val cellSize = size.width / 3f

                    val startIndex = winningLine.first()
                    val endIndex = winningLine.last()

                    val startRow = startIndex / 3
                    val startColumn = startIndex % 3

                    val endRow = endIndex / 3
                    val endColumn = endIndex % 3

                    val startX =
                        startColumn * cellSize + cellSize / 2

                    val startY =
                        startRow * cellSize + cellSize / 2

                    val endX =
                        endColumn * cellSize + cellSize / 2

                    val endY =
                        endRow * cellSize + cellSize / 2

                    // Glow
                    drawLine(
                        color = Color(0x55FFD700),
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 14f,
                        cap = StrokeCap.Round
                    )

                    // الخط الأساسي
                    drawLine(
                        color = Color(0xFFFFD700),
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 6f,
                        cap = StrokeCap.Round
                    )
                }
            }

        }

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        if (winner != null || isDraw) {

            Text(
                text = if (winner != null) {
                    "🏆 الفائز: $winner"
                } else {
                    "🤝 تعادل!"
                },
                color = Color(0xFFFFC107),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Button(
                onClick = {
                    gameViewModel.resetGame()
                }
            ) {
                Text(
                    text = "🔄 إعادة اللعب",
                    fontSize = 18.sp
                )
            }
            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    gameViewModel.resetScore()
                    onBackToMenu()
                }
            ) {
                Text(
                    text = "🏠 القائمة الرئيسية",
                    fontSize = 18.sp
                )
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
    isWinningCell: Boolean,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .size(95.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF673AB7),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = if (isWinningCell) {
                    Color(0xFF00C853)
                } else {
                    Color(0xFF252532)
                },
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