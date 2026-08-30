package com.fsociety.tictactoe.domain.ai

import com.fsociety.tictactoe.domain.GameLogic

object HardAi {


    fun getMove(
        board: List<String>,
        phoneMark: String,
        humanMark: String
    ): Int? {

        val availableMoves =
            GameLogic.getAvailableMoves(board)

        if (availableMoves.isEmpty()) {
            return null
        }

        return findBestMove(
            board = board,
            phoneMark = phoneMark,
            humanMark = humanMark
        )
    }


    private fun findBestMove(
        board: List<String>,
        phoneMark: String,
        humanMark: String
    ): Int {

        val availableMoves =
            GameLogic.getAvailableMoves(board)

        var bestScore = Int.MIN_VALUE

        var bestMove =
            availableMoves.first()

        for (move in availableMoves) {

            val testBoard =
                board.toMutableList()

            testBoard[move] = phoneMark

            val score = minimax(
                board = testBoard,
                depth = 0,
                isMaximizing = false,
                phoneMark = phoneMark,
                humanMark = humanMark
            )

            if (score > bestScore) {

                bestScore = score

                bestMove = move
            }
        }

        return bestMove
    }

    private fun minimax(
        board: MutableList<String>,
        depth: Int,
        isMaximizing: Boolean,
        phoneMark: String,
        humanMark: String
    ): Int {

        val winner =
            GameLogic.checkWinner(board)

        if (winner == phoneMark) {
            return 10 - depth
        }

        if (winner == humanMark) {
            return depth - 10
        }

        if (GameLogic.isDraw(board)) {
            return 0
        }

        return if (isMaximizing) {

            var bestScore = Int.MIN_VALUE

            val availableMoves =
                GameLogic.getAvailableMoves(board)

            for (move in availableMoves) {

                board[move] = phoneMark

                val score = minimax(
                    board = board,
                    depth = depth + 1,
                    isMaximizing = false,
                    phoneMark = phoneMark,
                    humanMark = humanMark
                )

                board[move] = ""

                bestScore =
                    maxOf(bestScore, score)
            }

            bestScore

        } else {

            var bestScore = Int.MAX_VALUE

            val availableMoves =
                GameLogic.getAvailableMoves(board)

            for (move in availableMoves) {

                board[move] = humanMark

                val score = minimax(
                    board = board,
                    depth = depth + 1,
                    isMaximizing = true,
                    phoneMark = phoneMark,
                    humanMark = humanMark
                )

                board[move] = ""

                bestScore =
                    minOf(bestScore, score)
            }

            bestScore
        }
    }
}