package com.example.quizmarket.ui.quiz.passing

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizmarket.R
import com.example.quizmarket.domain.models.requests.AnswerRequest
import com.example.quizmarket.domain.models.requests.QuizRequest
import com.example.quizmarket.domain.models.response.QuizResponse
import com.example.quizmarket.ui.composable.QuizMarketTextField
import com.example.quizmarket.ui.theme.Pinklight
import com.example.quizmarket.ui.theme.QuizMarketTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class QuizPassingActivity : ComponentActivity() {
    private val viewModel: QuizPassingViewModel by viewModel()
    private lateinit var quiz: QuizResponse
    private var questionsIndex: Int = 0
    private lateinit var token: String
    private var userId: Long = 0

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMarketTheme {
                val quiz = intent.getSerializableExtra("quiz", QuizResponse::class.java)
                token = getSharedPreferences("accessToken", MODE_PRIVATE).getString("accessToken", "").toString()
                userId = getSharedPreferences("userId", MODE_PRIVATE).getLong("userId", 0)
                if (quiz != null) {
                    this.quiz = quiz
                    getSharedPreferences("accessToken", MODE_PRIVATE).getString("accessToken", "")?.let {
                        viewModel.getAllQuizQuestionsByQuizId(it, quiz.id)
                    }
                    QuizScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun QuizScreen() {
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = quiz.title) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                finish()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = "arrow_back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Pinklight,
                        navigationIconContentColor = Color.White,
                        titleContentColor = Color.White
                    )
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                NavGraph(navHostController = navController)
            }
        }
    }


    @Composable
    private fun QuizQuestionScreen(
        navController: NavController,
    ) {
        val questions by viewModel.questions.collectAsState()
        val answers by viewModel.answers.collectAsState()
        val answer = remember { mutableStateOf("") }

        if (questions.isNotEmpty()) {
            Column(modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()) {
                Text(text = "Question:", fontSize = 20.sp, color = Color.DarkGray)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp)
                ) {
                    Box(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = questions[questionsIndex].content,
                            color = Color.DarkGray,
                            fontSize = 20.sp
                        )
                    }
                }
                Text(text = "Enter Answer:", fontSize = 20.sp, color = Color.DarkGray)
                QuizMarketTextField(variable = answer, label = "Answer")
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            answers.add(
                                AnswerRequest(
                                    listOf(answer.value),
                                    questions[questionsIndex].id
                                )
                            )
                            if (questionsIndex.toLong() == quiz.countQuestions - 1) {
                                viewModel.passQuiz(token, quiz.id, userId)
                                viewModel.updateQuizInfo(
                                    token,
                                    quiz.id,
                                    QuizRequest(
                                        title = quiz.title,
                                        type = quiz.type,
                                        description = quiz.description,
                                        countQuestions = quiz.countQuestions,
                                        countOfAnswers = quiz.countOfAnswers,
                                        authorNickname = quiz.authorNickname,
                                        countOfPassing = (quiz.countOfPassing.toLong() + 1).toString()
                                    )
                                )
                                finish()
                            } else {
                                questionsIndex++
                                navController.navigate("QuizQuestionScreen")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Pinklight,
                            contentColor = Color.White
                        )
                    ) {
                        if (questionsIndex.toLong() == quiz.countQuestions - 1) {
                            Text(text = "Finish Quiz", fontSize = 20.sp, color = Color.White)
                        } else {
                            Text(text = "Answer", fontSize = 20.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun NavGraph(
        navHostController: NavHostController,
    ) {
        NavHost(navController = navHostController, startDestination = "QuizQuestionScreen") {
            composable("QuizQuestionScreen") {
                QuizQuestionScreen(navHostController)
            }
        }
    }
}
