package com.example.quizmarket.ui.main.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quizmarket.domain.models.response.QuizResponse
import com.example.quizmarket.ui.main.MainViewModel
import com.example.quizmarket.ui.main.navigation.NavigationItem

@Composable
fun NavGraph(
    navHostController: NavHostController,
    quiz: MutableState<QuizResponse>,
    token: String,
    viewModel: MainViewModel
) {
    NavHost(navController = navHostController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            MainScreen(
                navController = navHostController,
                quiz = quiz,
                token = token,
                viewModel = viewModel
            )
        }
        composable(NavigationItem.Quiz.route) {
            QuizPreviewScreen(navController = navHostController, quiz = quiz, token = token, viewModel = viewModel)
        }
    }
}