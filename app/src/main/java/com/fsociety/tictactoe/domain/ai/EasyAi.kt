package com.fsociety.tictactoe.domain.ai

import com.fsociety.tictactoe.domain.GameLogic

object EasyAi {
    fun getMove(board: List<String>): Int?{
        val availableMoves = GameLogic.getAvailableMoves(board)
        if(availableMoves.isEmpty()){
            
        }
        return availableMoves.random()
    }
}