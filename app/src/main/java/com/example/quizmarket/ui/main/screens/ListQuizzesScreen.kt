package com.example.quizmarket.ui.main.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizmarket.R
import com.example.quizmarket.domain.models.response.QuizResponse
import com.example.quizmarket.ui.main.MainViewModel
import com.example.quizmarket.ui.main.navigation.NavigationItem
import org.koin.androidx.compose.koinViewModel

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListQuizzesScreen(
    navController: NavController,
    quiz: MutableState<QuizResponse>,
    viewModel: MainViewModel
) {
    val quizzes by viewModel.quizzes.collectAsState()
    Log.i("quizzes52", quizzes.toString())
    val isLoading by viewModel.isLoading.collectAsState()
    Log.i("isLoading", isLoading.toString())

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(quizzes.size) { i ->
                quizzes[i].let { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        onClick = {
                            quiz.value = item
                            navController.navigate(NavigationItem.Quiz.route)
                        }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.test),
                                contentDescription = "test",
                                tint = Color.DarkGray
                            )
                            Text(
                                text = item.title,
                                fontSize = 20.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}