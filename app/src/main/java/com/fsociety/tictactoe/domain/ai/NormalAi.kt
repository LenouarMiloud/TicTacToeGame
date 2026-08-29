package com.fsociety.tictactoe.domain.ai

import com.fsociety.tictactoe.domain.GameLogic

object NormalAi {

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

        // 1. محاولة الفوز
        for (move in availableMoves) {

            val testBoard =
                board.toMutableList()

            testBoard[move] = phoneMark

            if (GameLogic.checkWinner(testBoard) == phoneMark) {
                return move
            }
        }

        // 2. منع اللاعب من الفوز
        for (move in availableMoves) {

            val testBoard =
                board.toMutableList()

            testBoard[move] = humanMark

            if (GameLogic.checkWinner(testBoard) == humanMark) {
                return move
            }
        }

        // 3. إذا لم توجد حركة ضرورية، اختر حركة عشوائية
        return availableMoves.random()
    }
}