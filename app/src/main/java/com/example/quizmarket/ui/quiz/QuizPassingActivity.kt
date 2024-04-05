package com.example.quizmarket.ui.quiz

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizmarket.R
import com.example.quizmarket.domain.models.QuizResponse
import com.example.quizmarket.ui.theme.Pink200
import com.example.quizmarket.ui.theme.QuizMarketTheme
import com.example.quizmarket.ui.аuthentication.composable.LoginField
import org.koin.androidx.compose.koinViewModel

class QuizPassingActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMarketTheme {
                val quiz = intent.getSerializableExtra("quiz", QuizResponse::class.java)
                val viewModel: QuizPassingViewModel = koinViewModel()
                if (quiz != null) {
                    viewModel.setQuiz(quiz)
                    QuizScreen(viewModel = viewModel)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun QuizScreen(viewModel: QuizPassingViewModel = koinViewModel()) {
        val navController = rememberNavController()
        val questionsIndex = remember { mutableStateOf(1) }
        val quiz by viewModel.quiz.collectAsState()

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
                        containerColor = Pink200,
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
                NavGraph(navHostController = navController, questionsIndex = questionsIndex , viewModel = viewModel)
            }
        }
    }


    @Composable
    fun QuizQuestionScreen(
        navController: NavController,
        viewModel: QuizPassingViewModel = koinViewModel(),
        questionsIndex: MutableState<Int>
    ) {
        val quiz by viewModel.quiz.collectAsState()
        val answer = remember {
            mutableStateOf("")
        }

        Column (modifier = Modifier.fillMaxSize()){
            Card(
                modifier = Modifier.padding(10.dp).fillMaxWidth()
            ) {
                Box(modifier = Modifier.padding(5.dp)) {
                    Text(
                        text = "What is 2 + 2?",
                        color = Color.DarkGray,
                        fontSize = 20.sp
                    )
                }
            }
            TextField(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                value = answer.value,
                shape = RoundedCornerShape(15.dp),
                textStyle = TextStyle(fontSize = 20.sp, color = Color.DarkGray),
                singleLine = true,
                onValueChange = { newValue -> answer.value = newValue },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color.DarkGray,
                    disabledLabelColor = Color.Gray,
                    unfocusedIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White
                ),
                placeholder = { Text(text = answer.value) },
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ){
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    onClick = {
                        questionsIndex.value++
                        if (quiz.countQuestions >= questionsIndex.value) {
                            navController.navigate("QuizQuestionScreen")
                        } else {
                            finish()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Pink200,
                        contentColor = Color.White
                    )
                ) {
                    if (quiz.countQuestions > questionsIndex.value) {
                        Text(text = "Pass quiz", fontSize = 20.sp)
                    } else {
                        Text(text = "Finish Quiz", fontSize = 20.sp)
                    }
                }
            }
        }
    }

    @Composable
    fun NavGraph(
        navHostController: NavHostController,
        questionsIndex: MutableState<Int>,
        viewModel: QuizPassingViewModel
    ) {
        NavHost(navController = navHostController, startDestination = "QuizQuestionScreen") {
            composable("QuizQuestionScreen") {
                QuizQuestionScreen(navHostController, viewModel, questionsIndex)
            }
        }
    }
}
