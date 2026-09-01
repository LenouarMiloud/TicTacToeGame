package com.fsociety.tictactoe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fsociety.tictactoe.ui.screens.Difficulty
import com.fsociety.tictactoe.ui.screens.FirstPlayer
import com.fsociety.tictactoe.ui.screens.GameModeScreen
import com.fsociety.tictactoe.ui.screens.GameScreen
import com.fsociety.tictactoe.ui.screens.GameType
import com.fsociety.tictactoe.ui.screens.MainMenuScreen
import com.fsociety.tictactoe.ui.screens.Screen

@Composable
fun AppNavigation(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.MainMenu.route
    ){

        composable(
            route = Screen.MainMenu.route
        ) {

            MainMenuScreen(

                onPlayerVsPlayerClick = {

                    navController.navigate(
                        "game/PLAYER_VS_PLAYER/EASY/HUMAN"
                    )
                },

                onPlayerVsPhoneClick = {

                    navController.navigate(
                        route = Screen.GameMode.route
                    )
                }
            )
        }

        composable(
            route = Screen.GameMode.route
        ) {

            GameModeScreen(

                onStartGame = { difficulty, firstPlayer ->

                    navController.navigate(
                        "game/${GameType.PLAYER_VS_PHONE.name}/${difficulty.name}/${firstPlayer.name}"
                    )
                }
            )
        }

        composable(
            route = Screen.Game.route,
            arguments = listOf(
                navArgument("gameType") {
                    type = NavType.StringType
                },
                navArgument("difficulty") {
                    type = NavType.StringType
                },
                navArgument("firstPlayer") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val gameType =
                GameType.valueOf(
                    backStackEntry.arguments
                        ?.getString("gameType")
                        ?: GameType.PLAYER_VS_PHONE.name
                )

            val difficulty =
                Difficulty.valueOf(
                    backStackEntry.arguments
                        ?.getString("difficulty")
                        ?: Difficulty.EASY.name
                )

            val firstPlayer =
                FirstPlayer.valueOf(
                    backStackEntry.arguments
                        ?.getString("firstPlayer")
                        ?: FirstPlayer.HUMAN.name
                )

            GameScreen(
                gameType = gameType,
                difficulty = difficulty,
                firstPlayer = firstPlayer,
                onBackToMenu = {
                    navController.popBackStack()
                }
            )
        }
    }
}