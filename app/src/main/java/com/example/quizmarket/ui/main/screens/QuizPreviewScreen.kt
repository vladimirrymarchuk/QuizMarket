package com.example.quizmarket.ui.main.screens

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizmarket.R
import com.example.quizmarket.domain.models.response.QuizResponse
import com.example.quizmarket.ui.main.MainActivity
import com.example.quizmarket.ui.main.MainViewModel
import com.example.quizmarket.ui.main.navigation.NavigationItem
import com.example.quizmarket.ui.quiz.passing.QuizPassingActivity
import com.example.quizmarket.ui.theme.Pinklight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizPreviewScreen(
    quiz: MutableState<QuizResponse>,
    navController: NavController,
    token: String,
    viewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = NavigationItem.Quiz.title, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Pinklight),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(NavigationItem.Home.route)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = "back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Card(
                modifier = Modifier.padding(10.dp),
                colors = CardDefaults.cardColors(contentColor = Color.DarkGray)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = quiz.value.title, fontSize = 30.sp)
                }
                Column(modifier = Modifier.padding(5.dp)) {
                    Text(text = "Type: ${quiz.value.type}", fontSize = 20.sp)
                    Text(
                        text = "Autor Nickname: ${quiz.value.authorNickname}",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Count questions: ${quiz.value.countQuestions}",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Count of answers: ${quiz.value.countOfAnswers}",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Count of passing: ${quiz.value.countOfPassing}",
                        fontSize = 20.sp
                    )
                    Text(text = "Created at: ${quiz.value.createdAt}", fontSize = 20.sp)
                    Text(text = "description: ${quiz.value.description}", fontSize = 20.sp)
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    onClick = {
                        Intent(
                            activity,
                            QuizPassingActivity::class.java
                        ).also { intent ->
                            intent.putExtra("quiz", quiz.value)
                            activity.startActivity(intent)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Pinklight,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Pass quiz", fontSize = 20.sp)
                }
            }
        }
    }
}
