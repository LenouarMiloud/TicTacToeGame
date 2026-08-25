package com.fsociety.tictactoe.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _board = MutableStateFlow(
        List(9) { "" }
    )

    val board: StateFlow<List<String>> = _board.asStateFlow()

    private val _currentPlayer = MutableStateFlow("X")

    val currentPlayer: StateFlow<String> =
        _currentPlayer.asStateFlow()


    fun makeMove(index: Int) {

        if (_board.value[index].isNotEmpty()) {
            return
        }

        val newBoard = _board.value.toMutableList()

        newBoard[index] = _currentPlayer.value

        _board.value = newBoard

        _currentPlayer.value =
            if (_currentPlayer.value == "X") {
                "O"
            } else {
                "X"
            }
    }
}