package com.fsociety.tictactoe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsociety.tictactoe.domain.GameLogic
import com.fsociety.tictactoe.domain.ai.EasyAi
import com.fsociety.tictactoe.domain.ai.HardAi
import com.fsociety.tictactoe.domain.ai.NormalAi
import com.fsociety.tictactoe.ui.screens.Difficulty
import com.fsociety.tictactoe.ui.screens.FirstPlayer
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

    private val _isPhoneThinking =
        MutableStateFlow(false)

    val isPhoneThinking: StateFlow<Boolean> =
        _isPhoneThinking.asStateFlow()

    val isDraw: StateFlow<Boolean> =
        _isDraw.asStateFlow()


    private var difficulty =
        Difficulty.EASY

    private var humanMark = "X"

    private var phoneMark = "O"


    fun setupGame(
        difficulty: Difficulty,
        firstPlayer: FirstPlayer
    ) {

        this.difficulty = difficulty

        if (firstPlayer == FirstPlayer.HUMAN) {

            humanMark = "X"
            phoneMark = "O"

        } else {

            humanMark = "O"
            phoneMark = "X"
        }

        resetGame()

        if (firstPlayer == FirstPlayer.PHONE) {

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

        // لا يمكن للاعب اللعب خارج دوره
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

                _currentPlayer.value = humanMark
            }
        }
    }


    private fun checkGameState() {

        val winnerPlayer =
            GameLogic.checkWinner(_board.value)

        if (winnerPlayer != null) {

            _winner.value = winnerPlayer

            return
        }

        if (GameLogic.isDraw(_board.value)) {

            _isDraw.value = true
        }
    }


    fun isGameOver(): Boolean {

        return _winner.value != null ||
                _isDraw.value
    }


    fun resetGame() {

        _board.value =
            List(9) { "" }

        _currentPlayer.value = "X"

        _winner.value = null

        _isDraw.value = false

        _isPhoneThinking.value = false
    }
}