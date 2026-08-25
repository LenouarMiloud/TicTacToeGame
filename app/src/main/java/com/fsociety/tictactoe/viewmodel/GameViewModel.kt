package com.fsociety.tictactoe.viewmodel

import androidx.lifecycle.ViewModel
import com.fsociety.tictactoe.domain.GameLogic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    private val _board = MutableStateFlow(
        List(9) { "" }
    )

    val board: StateFlow<List<String>> =
        _board.asStateFlow()


    private val _currentPlayer = MutableStateFlow("X")

    val currentPlayer: StateFlow<String> =
        _currentPlayer.asStateFlow()


    private val _winner = MutableStateFlow<String?>(null)

    val winner: StateFlow<String?> =
        _winner.asStateFlow()


    private val _isDraw = MutableStateFlow(false)

    val isDraw: StateFlow<Boolean> =
        _isDraw.asStateFlow()


    fun makeMove(index: Int) {

        // اللعبة انتهت
        if (isGameOver()) {
            return
        }

        // الخانة مستخدمة
        if (_board.value[index].isNotEmpty()) {
            return
        }

        val newBoard = _board.value.toMutableList()

        newBoard[index] = _currentPlayer.value

        _board.value = newBoard

        // التحقق من الفائز
        val winnerPlayer =
            GameLogic.checkWinner(newBoard)

        if (winnerPlayer != null) {

            _winner.value = winnerPlayer

            return
        }

        // التحقق من التعادل
        if (GameLogic.isDraw(newBoard)) {

            _isDraw.value = true

            return
        }

        // تغيير اللاعب
        _currentPlayer.value =
            if (_currentPlayer.value == "X") {
                "O"
            } else {
                "X"
            }
    }


    fun isGameOver(): Boolean {

        return _winner.value != null ||
                _isDraw.value
    }


    fun resetGame() {

        _board.value = List(9) { "" }

        _currentPlayer.value = "X"

        _winner.value = null

        _isDraw.value = false
    }
}