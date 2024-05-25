package com.example.quizmarket.ui.quiz.create

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizmarket.R
import com.example.quizmarket.domain.models.requests.QuizRequest
import com.example.quizmarket.ui.composable.QuizMarketTextField
import com.example.quizmarket.ui.theme.Pinklight
import com.example.quizmarket.ui.theme.QuizMarketTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizConstructorActivity : ComponentActivity() {
    private val viewModel: QuizConstructorViewModel by viewModel()
    private var questionsIndex: Long = 0
    private var countQuestions: Long = 0
    private lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMarketTheme {
                token =
                    getSharedPreferences("accessToken", MODE_PRIVATE).getString("accessToken", "")
                        .toString()
                getSharedPreferences("userId", MODE_PRIVATE).getLong("userId", 0).let { id ->
                    viewModel.loadUser(id)
                }
                CreateQuizScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CreateQuizScreen() {
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Constructor") },
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
                    .padding(5.dp)
            ) {
                NavGraph(navHostController = navController)
            }
        }
    }


    @Composable
    private fun QuizInfoScreen(navController: NavController) {
        val user by viewModel.user.collectAsState()
        val title = remember {
            mutableStateOf("")
        }
        val type = remember {
            mutableStateOf("Free answers")
        }
        val countQuestions = remember {
            mutableLongStateOf(1L)
        }
        val description = remember {
            mutableStateOf("")
        }
        val selectedTabIndex = remember {
            mutableLongStateOf(0L)
        }

        val quizTypes = listOf(
            "Free answers",
            "Fixed answers"
        )


        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Enter quiz name:", fontSize = 20.sp, color = Color.DarkGray)
            Box(modifier = Modifier.padding(bottom = 25.dp)) {
                QuizMarketTextField(
                    variable = title,
                    label = "title"
                )
            }
            Text(text = "Select quiz type:", fontSize = 20.sp, color = Color.DarkGray)
            Card(modifier = Modifier.padding(bottom = 25.dp)) {
                TabRow(
                    selectedTabIndex = selectedTabIndex.longValue.toInt(),
                    contentColor = Color.DarkGray,
                ) {
                    quizTypes.forEachIndexed { index, item ->
                        Tab(
                            selected = index.toLong() == selectedTabIndex.longValue,
                            onClick = {
                                selectedTabIndex.longValue = index.toLong()
                                Log.i("select", selectedTabIndex.longValue.toString())
                                type.value = item
                            },
                            text = { Text(text = item, color = Color.DarkGray) },
                        )
                    }
                }
            }
            Text(text = "Count question:", fontSize = 20.sp, color = Color.DarkGray)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 25.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    IconButton(onClick = { if (countQuestions.longValue > 1) countQuestions.longValue-- }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_ios),
                            contentDescription = "minus"
                        )
                    }
                    Text(
                        text = countQuestions.longValue.toString(),
                        modifier = Modifier.padding(10.dp),
                        fontSize = 20.sp
                    )
                    IconButton(onClick = { countQuestions.longValue++ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_forward),
                            contentDescription = "plus"
                        )
                    }
                }
            }
            Text(text = "Enter quiz description:", fontSize = 20.sp, color = Color.DarkGray)
            QuizMarketTextField(variable = description, label = "Description", singleLine = false)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    onClick = {
                        lifecycleScope.launch(Dispatchers.IO) {
                            viewModel.createQuiz(
                                token = token,
                                newQuiz = QuizRequest(
                                    title = title.value,
                                    type = type.value,
                                    description = description.value,
                                    countQuestions = countQuestions.longValue,
                                    countOfAnswers = countQuestions.longValue,
                                    authorNickname = user.name
                                )
                            )
                        }
                        this@QuizConstructorActivity.countQuestions = countQuestions.longValue
                        if (type.value == quizTypes[0]) {
                            navController.navigate("FreeAnswerQuestionScreen")
                        } else {
                            navController.navigate("FixedAnswerQuestionScreen")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Pinklight,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Continue", fontSize = 20.sp, color = Color.White)
                }
            }
        }
    }

    @Composable
    private fun NavGraph(
        navHostController: NavHostController,
    ) {
        NavHost(navController = navHostController, startDestination = "QuizInfoScreen") {
            composable("QuizInfoScreen") {
                QuizInfoScreen(navHostController)
            }
            composable("FreeAnswerQuestionScreen") {
                FreeAnswerQuestionScreen(navHostController)
            }
            composable("FixedAnswerQuestionScreen") {
                FixedAnswerQuestionScreen(navHostController)
            }
        }
    }


    @Composable
    private fun FreeAnswerQuestionScreen(navController: NavController) {
        val questions by viewModel.questions.collectAsState()
        val answers by viewModel.answers.collectAsState()

        val question = remember {
            mutableStateOf("")
        }
        val answer = remember {
            mutableStateOf("")
        }


        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Enter question:", fontSize = 20.sp, color = Color.DarkGray)
            Box(modifier = Modifier.padding(bottom = 25.dp)) {
                QuizMarketTextField(
                    variable = question,
                    label = "title",
                    singleLine = false
                )
            }
            Text(text = "Enter answer:", fontSize = 20.sp, color = Color.DarkGray)
            Box(modifier = Modifier.padding(bottom = 25.dp)) {
                QuizMarketTextField(
                    variable = answer,
                    label = "title",
                    singleLine = false
                )
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
                        questions.add(question.value)
                        answers.add(answer.value)
                        if (questionsIndex == countQuestions - 1) {
                            viewModel.addQuestions(token)
                            viewModel.addAnswers(token)
                            finish()
                        } else {
                            questionsIndex++
                            navController.navigate("FreeAnswerQuestionScreen")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Pinklight,
                        contentColor = Color.White
                    )
                ) {
                    if (questionsIndex == countQuestions - 1){
                        Text(text = "Finish", fontSize = 20.sp, color = Color.White)
                    } else {
                        Text(text = "Continue", fontSize = 20.sp, color = Color.White)
                    }
                }
            }
        }
    }

    @Composable
    private fun FixedAnswerQuestionScreen(navController: NavController) {

    }
}