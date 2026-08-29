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
                        "game/${Difficulty.MEDIUM.name}/${FirstPlayer.HUMAN.name}"
                    )
                },

                onPlayerVsPhoneClick = {

                    navController.navigate(
                        Screen.GameMode.route
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
                        "game/${difficulty.name}/${firstPlayer.name}"
                    )
                }
            )
        }

        composable(
            route = Screen.Game.route,

            arguments = listOf(
                navArgument("difficulty") {
                    type = NavType.StringType
                },

                navArgument("firstPlayer") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val difficulty = Difficulty.valueOf(
                backStackEntry.arguments
                    ?.getString("difficulty")
                    ?: Difficulty.MEDIUM.name
            )

            val firstPlayer = FirstPlayer.valueOf(
                backStackEntry.arguments
                    ?.getString("firstPlayer")
                    ?: FirstPlayer.HUMAN.name
            )

            GameScreen(
                difficulty = difficulty,
                firstPlayer = firstPlayer,

                onBackToMenu = {

                    navController.navigate(
                        Screen.MainMenu.route
                    ) {

                        popUpTo(
                            Screen.MainMenu.route
                        ) {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                }
            )
        }
    }
}