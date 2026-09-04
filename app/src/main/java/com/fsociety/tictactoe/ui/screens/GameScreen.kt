package com.fsociety.tictactoe.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fsociety.tictactoe.ui.theme.GameColors
import com.fsociety.tictactoe.viewmodel.GameViewModel
import kotlin.random.Random

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

    val lineProgress = remember { Animatable(0f) }

    LaunchedEffect(winningLine) {
        if (winningLine.isNotEmpty()) {
            lineProgress.snapTo(0f)
            lineProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 2000
                )
            )
        } else {
            lineProgress.snapTo(0f)
        }
    }

    LaunchedEffect(gameType,difficulty, firstPlayer) {

        gameViewModel.setupGame(
            gameType = gameType,
            difficulty = difficulty,
            firstPlayer = firstPlayer
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GameColors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "TIC TAC TOE",
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color = GameColors.X,
                modifier = Modifier.shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(8.dp),
                    clip = false
                )
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
                        color = GameColors.X,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "🤝",
                        color = GameColors.White,
                        fontSize = 18.sp
                    )

                    Text(
                        text = drawScore.toString(),
                        color = GameColors.Gold,
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
                        color = GameColors.O,
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

                color = GameColors.X,

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

                        val animatedEndX =
                            startX + (endX - startX) * lineProgress.value

                        val animatedEndY =
                            startY + (endY - startY) * lineProgress.value

                        // Glow
                        drawLine(
                            color = GameColors.GoldGlow,
                            start = Offset(startX, startY),
                            end = Offset(animatedEndX, animatedEndY),
                            strokeWidth = 14f,
                            cap = StrokeCap.Round
                        )

                        // الخط الأساسي
                        drawLine(
                            color = GameColors.Gold,
                            start = Offset(startX, startY),
                            end = Offset(animatedEndX, animatedEndY),
                            strokeWidth = 6f,
                            cap = StrokeCap.Round
                        )
                    }
                }

            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            val resultScale by animateFloatAsState(
                targetValue = if (winner != null || isDraw) 1f else 0.5f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                ),
                label = "resultScale"
            )
            val showResult = winner != null || isDraw

            if (winner != null || isDraw) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .graphicsLayer {
                            scaleX = resultScale
                            scaleY = resultScale
                        }
                        .background(
                            color = GameColors.Surface,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = GameColors.Gold,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(
                            horizontal = 24.dp,
                            vertical = 20.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (winner != null) {
                            "🏆 الفائز: $winner"
                        } else {
                            "🤝 تعادل!"
                        },
                        color = GameColors.Gold,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Button(
                    onClick = {
                        gameViewModel.resetGame()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp)
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
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp)
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
                color = GameColors.ConfettiYellow
            )
        }
        ConfettiEffect(
            isVisible = winner != null
        )
    }
}


@Composable
private fun GameCell(
    value: String,
    isWinningCell: Boolean,
    onClick: () -> Unit
) {

    val infiniteTransition = rememberInfiniteTransition(
        label = "winningPulse"
    )

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = Modifier
            .size(95.dp)
            .graphicsLayer {
                scaleX = if (isWinningCell) pulseScale else 1f
                scaleY = if (isWinningCell) pulseScale else 1f
            }
            .border(
                width = 2.dp,
                color = GameColors.Primary,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = if (isWinningCell) {
                    GameColors.WinGreen
                } else {
                    GameColors.CellBackground
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
                GameColors.X
            } else {
                GameColors.O
            }
        )
    }
}

@Composable
private fun ConfettiEffect(
    isVisible: Boolean
) {
    if (!isVisible) return

    val particles = remember {
        List(45) {
            ConfettiParticle(
                x = Random.nextFloat(),
                y = Random.nextFloat() * -0.5f - 0.1f,
                targetY = Random.nextFloat() * 0.75f + 0.15f,
                size = Random.nextFloat() * 8f + 5f,
                speed = Random.nextFloat() * 0.008f + 0.004f,
                rotation = Random.nextFloat() * 360f,
                rotationSpeed = Random.nextFloat() * 8f - 4f,
                delay = Random.nextFloat() * 0.25f
            )
        }
    }

    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(3500)
        )
    }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        particles.forEach { particle ->

            val particleProgress =
                ((progress.value - particle.delay) /
                        (1f - particle.delay))
                    .coerceIn(0f, 1f)

            val alpha =
                if (particleProgress < 0.7f) {
                    1f
                } else {
                    1f - ((particleProgress - 0.7f) / 0.3f)
                }

            val y =
                particle.y +
                        (particle.targetY - particle.y) * particleProgress

            val sway =
                kotlin.math.sin(
                    particleProgress * 12f +
                            particle.x * 20f
                ) * 0.04f

            val x =
                particle.x + sway

            val rotation =
                particle.rotation +
                        particle.rotationSpeed *
                        particleProgress *
                        360f

            drawConfettiParticle(
                particle = particle,
                x = x * size.width,
                y = y * size.height,
                rotation = rotation,
                alpha = alpha
            )
        }
    }
}
private data class ConfettiParticle(
    val x: Float,
    val y: Float,
    val targetY: Float,
    val size: Float,
    val speed: Float,
    val rotation: Float,
    val rotationSpeed: Float,
    val delay: Float
)

private fun DrawScope.drawConfettiParticle(
    particle: ConfettiParticle,
    x: Float,
    y: Float,
    rotation: Float,
    alpha: Float
) {
    val colors = listOf(
        GameColors.ConfettiPink,
        GameColors.ConfettiCyan,
        GameColors.ConfettiYellow,
        GameColors.ConfettiGreen,
        GameColors.ConfettiPurple,
        GameColors.ConfettiOrange
    )

    val color = colors[
        ((particle.x * colors.size).toInt())
            .coerceIn(0, colors.lastIndex)
    ]

    rotate(
        degrees = rotation,
        pivot = Offset(x, y)
    ) {
        drawRect(
            color = color,
            alpha = alpha,
            topLeft = Offset(
                x - particle.size / 2,
                y - particle.size / 2
            ),
            size = androidx.compose.ui.geometry.Size(
                particle.size,
                particle.size * 1.8f
            )
        )
    }
}