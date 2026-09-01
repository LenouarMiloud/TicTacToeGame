package com.fsociety.tictactoe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsociety.tictactoe.domain.GameLogic
import com.fsociety.tictactoe.domain.ai.EasyAi
import com.fsociety.tictactoe.domain.ai.HardAi
import com.fsociety.tictactoe.domain.ai.NormalAi
import com.fsociety.tictactoe.ui.screens.Difficulty
import com.fsociety.tictactoe.ui.screens.FirstPlayer
import com.fsociety.tictactoe.ui.screens.GameType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val _board =
        MutableStateFlow(List(9) { "" })

    val board: StateFlow<List<String>> =
        _board.asStateFlow()


    private val _currentPlayer =
        MutableStateFlow("X")

    val currentPlayer: StateFlow<String> =
        _currentPlayer.asStateFlow()


    private val _winner =
        MutableStateFlow<String?>(null)

    val winner: StateFlow<String?> =
        _winner.asStateFlow()


    private val _isDraw =
        MutableStateFlow(false)

    val isDraw: StateFlow<Boolean> =
        _isDraw.asStateFlow()


    private val _isPhoneThinking =
        MutableStateFlow(false)

    val isPhoneThinking: StateFlow<Boolean> =
        _isPhoneThinking.asStateFlow()


    private var gameType =
        GameType.PLAYER_VS_PHONE

    private var difficulty =
        Difficulty.EASY

    private var humanMark = "X"

    private var phoneMark = "O"


    private val _humanScore =
        MutableStateFlow(0)

    val humanScore: StateFlow<Int> =
        _humanScore.asStateFlow()


    private val _phoneScore =
        MutableStateFlow(0)

    val phoneScore: StateFlow<Int> =
        _phoneScore.asStateFlow()


    private val _drawScore =
        MutableStateFlow(0)

    val drawScore: StateFlow<Int> =
        _drawScore.asStateFlow()


    fun setupGame(
        gameType: GameType,
        difficulty: Difficulty,
        firstPlayer: FirstPlayer
    ) {

        this.gameType = gameType
        this.difficulty = difficulty

        if (gameType == GameType.PLAYER_VS_PHONE) {

            if (firstPlayer == FirstPlayer.HUMAN) {

                humanMark = "X"
                phoneMark = "O"

            } else {

                humanMark = "O"
                phoneMark = "X"
            }

        } else {

            // في Player vs Player
            // لا يوجد Human/Phone
            // X يبدأ دائمًا

            humanMark = "X"
            phoneMark = "O"
        }

        resetGame()

        if (
            gameType == GameType.PLAYER_VS_PHONE &&
            firstPlayer == FirstPlayer.PHONE
        ) {

            makePhoneMove()
        }
    }


    fun makeMove(index: Int) {

        if (isGameOver()) {
            return
        }

        if (_isPhoneThinking.value) {
            return
        }

        if (_board.value[index].isNotEmpty()) {
            return
        }


        /*
         * PLAYER VS PLAYER
         */
        if (gameType == GameType.PLAYER_VS_PLAYER) {

            val newBoard =
                _board.value.toMutableList()

            newBoard[index] =
                _currentPlayer.value

            _board.value = newBoard

            checkGameState()

            if (isGameOver()) {
                return
            }

            // تبديل X ↔ O
            _currentPlayer.value =
                if (_currentPlayer.value == "X") {
                    "O"
                } else {
                    "X"
                }

            return
        }


        /*
         * PLAYER VS PHONE
         */

        // لا يمكن للاعب اللعب إلا في دوره
        if (_currentPlayer.value != humanMark) {
            return
        }

        val newBoard =
            _board.value.toMutableList()

        newBoard[index] = humanMark

        _board.value = newBoard

        checkGameState()

        if (isGameOver()) {
            return
        }

        _currentPlayer.value = phoneMark

        makePhoneMove()
    }


    private fun makePhoneMove() {

        if (gameType != GameType.PLAYER_VS_PHONE) {
            return
        }

        if (isGameOver()) {
            return
        }

        _isPhoneThinking.value = true

        viewModelScope.launch {

            delay(500)

            if (isGameOver()) {

                _isPhoneThinking.value = false

                return@launch
            }

            val move = when (difficulty) {

                Difficulty.EASY -> {

                    EasyAi.getMove(
                        _board.value
                    )
                }

                Difficulty.MEDIUM -> {

                    NormalAi.getMove(
                        board = _board.value,
                        phoneMark = phoneMark,
                        humanMark = humanMark
                    )
                }

                Difficulty.HARD -> {

                    HardAi.getMove(
                        board = _board.value,
                        phoneMark = phoneMark,
                        humanMark = humanMark
                    )
                }
            }


            if (move != null) {

                val newBoard =
                    _board.value.toMutableList()

                newBoard[move] = phoneMark

                _board.value = newBoard

                checkGameState()
            }


            _isPhoneThinking.value = false

            if (!isGameOver()) {

                _currentPlayer.value =
                    humanMark
            }
        }
    }


    private fun checkGameState() {

        val winnerPlayer =
            GameLogic.checkWinner(_board.value)

        if (winnerPlayer != null) {

            _winner.value = winnerPlayer

            if (gameType == GameType.PLAYER_VS_PLAYER) {

                if (winnerPlayer == "X") {
                    _humanScore.value++
                } else {
                    _phoneScore.value++
                }

            } else {

                if (winnerPlayer == humanMark) {
                    _humanScore.value++
                } else {
                    _phoneScore.value++
                }
            }

            return
        }


        if (GameLogic.isDraw(_board.value)) {

            _isDraw.value = true

            _drawScore.value++
        }
    }


    fun isGameOver(): Boolean {

        return _winner.value != null ||
                _isDraw.value
    }


    fun resetGame() {

        _board.value =
            List(9) { "" }

        _currentPlayer.value =
            "X"

        _winner.value = null

        _isDraw.value = false

        _isPhoneThinking.value = false
    }


    fun resetScore() {

        _humanScore.value = 0

        _phoneScore.value = 0

        _drawScore.value = 0
    }
}