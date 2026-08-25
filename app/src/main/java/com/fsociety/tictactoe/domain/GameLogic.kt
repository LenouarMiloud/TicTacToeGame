package com.fsociety.tictactoe.domain

object GameLogic {
    private val winningPositions = listOf(
        listOf(0,1,2),
        listOf(3,4,5),
        listOf(6,7,8),

        listOf(0,3,6),
        listOf(1,4,7),
        listOf(2,5,8),

        listOf(0,4,8),
        listOf(2,4,6),
    )
    fun checkWinner(board:List<String>): String?{
        for(position in winningPositions){
            val first = board[position[0]]
            val second = board[position[1]]
            val third = board[position[2]]

            if(first.isNotEmpty() && first == second && second == third){
                return first
            }
        }
        return null
    }
    fun isDraw(board: List<String>): Boolean {

        return board.all {
            it.isNotEmpty()
        } && checkWinner(board) == null
    }

    fun isGameOver(board: List<String>): Boolean {

        return checkWinner(board) != null ||
                isDraw(board)
    }

    fun getAvailableMoves(board: List<String>): List<Int> {

        return board.mapIndexedNotNull { index, value ->

            if (value.isEmpty()) {
                index
            } else {
                null
            }
        }
    }
}