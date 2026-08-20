package com.fsociety.tictactoe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fsociety.tictactoe.ui.screens.GameModeScreen
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
                        Screen.Game.route
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
                        Screen.Game.route
                    )
                }
            )
        }

        composable(
            route = Screen.Game.route
        ) {

        }
    }
}