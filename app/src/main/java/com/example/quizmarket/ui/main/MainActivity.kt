package com.example.quizmarket.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.quizmarket.domain.models.response.QuizResponse
import com.example.quizmarket.ui.main.screens.NavGraph
import com.example.quizmarket.ui.theme.QuizMarketTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMarketTheme {
                val token = getSharedPreferences(
                    "accessToken",
                    MODE_PRIVATE
                ).getString("accessToken", "").toString()
                val viewModel = koinViewModel<MainViewModel>()
                val navController = rememberNavController()
                val quiz = remember { mutableStateOf(QuizResponse()) }
                viewModel.loadQuizzes(token)
                NavGraph(navHostController = navController, quiz = quiz, token = token, viewModel = viewModel)
            }
        }
    }
}
