package com.fsociety.tictactoe.ui.screens

sealed class Screen(
    val route: String
) {

    data object MainMenu : Screen("main_menu")

    data object GameMode : Screen("game_mode")

    data object Game : Screen("game")
}