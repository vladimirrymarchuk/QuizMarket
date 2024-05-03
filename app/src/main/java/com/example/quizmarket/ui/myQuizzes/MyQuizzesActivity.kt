package com.example.quizmarket.ui.myQuizzes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizmarket.R
import com.example.quizmarket.domain.models.QuizRequest
import com.example.quizmarket.domain.models.QuizResponse
import com.example.quizmarket.domain.models.UserResponse
import com.example.quizmarket.ui.composable.QuizMarketTextField
import com.example.quizmarket.ui.main.items.NavigationItem
import com.example.quizmarket.ui.theme.Pink200
import com.example.quizmarket.ui.theme.QuizMarketTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MyQuizzesActivity : ComponentActivity() {
    private lateinit var viewModel: MyQuizzesViewModel
    private var userId: Long = 0
    private lateinit var token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = koinViewModel()
            token =
                getSharedPreferences("accessToken", MODE_PRIVATE).getString("accessToken", "")
                    .toString()
            userId = getSharedPreferences("userId", MODE_PRIVATE).getLong("userId", 0)
            val isLoading by viewModel.isLoading.collectAsState()
            viewModel.loadUser(userId)
            val navController = rememberNavController()
            val quiz = remember { mutableStateOf(QuizResponse()) }
            val user = remember {
                mutableStateOf(UserResponse())
            }
            initSubscribeLoadUser()
            QuizMarketTheme {
                if (isLoading)
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                else NavGraph(navController, quiz, user)
            }
        }
    }

    private fun initSubscribeLoadUser() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.user.collect {
                if (it.name != "") {
                    viewModel.loadQuizzes(token)
                }
            }
        }
    }

    private fun initSubscribeDelete() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.isDeleting.collect {
                if (!it) {
                    viewModel.loadQuizzes(token)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MyQuizzesScreen(
        navController: NavController,
        quiz: MutableState<QuizResponse>,
        user: MutableState<UserResponse>
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "My quizzes", color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink200),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                finish()
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.arrow_back),
                                contentDescription = "back",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                viewModel.loadQuizzes(token = token)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.restore),
                                contentDescription = "restore",
                                tint = Color.White
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                AllQuizzesScreen(navController = navController, quiz = quiz)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AllQuizzesScreen(navController: NavController, quiz: MutableState<QuizResponse>) {
        val quizzes by viewModel.quizzes.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()

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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                        ) {
                            Card(
                                onClick = {
                                    quiz.value = item
                                    navController.navigate(NavigationItem.Quiz.route)
                                }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(0.85f),
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
                            IconButton(
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 7.dp),
                                onClick = {
                                    viewModel.deleteQuiz(token, item)
                                    initSubscribeDelete()
                                },
                                colors = IconButtonDefaults.iconButtonColors(containerColor = Pink200)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.delete),
                                    contentDescription = "delete",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun NavGraph(
        navHostController: NavHostController,
        quiz: MutableState<QuizResponse>,
        user: MutableState<UserResponse>
    ) {
        NavHost(navController = navHostController, startDestination = NavigationItem.Home.route) {
            composable(NavigationItem.Home.route) {
                MyQuizzesScreen(navController = navHostController, quiz = quiz, user = user)
            }
            composable(NavigationItem.Quiz.route) {
                QuizPreviewScreen(navController = navHostController, quiz = quiz, user = user)
            }
            composable("AllAnswersScreen") {
                AllAnswerScreen(navHostController, quiz, user)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AllAnswerScreen(
        navController: NavController,
        quiz: MutableState<QuizResponse>,
        user: MutableState<UserResponse>
    ) {
        val answers by viewModel.answers.collectAsState()
        viewModel.getAllQuizAnswersByQuizId(token, quiz.value.id, user.value.id)
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = user.value.name, color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink200),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigate(NavigationItem.Quiz.route)
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
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(5.dp)
            ) {
                LazyColumn() {
                    items(answers.size) { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth().padding(vertical = 5.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                            ) {
                                Text(
                                    text = answers[index].content ?: "null",
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun QuizPreviewScreen(
        navController: NavController,
        quiz: MutableState<QuizResponse>,
        user: MutableState<UserResponse>
    ) {
        val title = remember {
            mutableStateOf(quiz.value.title)
        }
        val description = remember {
            mutableStateOf(quiz.value.description)
        }
        viewModel.allWhoPassed(token, quiz.value.id)
        val users by viewModel.users.collectAsState()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Quiz", color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink200),
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
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 5.dp, vertical = 7.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.85F)
                        ) {
                            QuizMarketTextField(
                                variable = title,
                                label = "title",
                                singleLine = false
                            )
                        }
                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                viewModel.updateQuizInfo(
                                    token,
                                    quiz.value.id,
                                    QuizRequest(
                                        title = title.value,
                                        type = quiz.value.type,
                                        description = quiz.value.description,
                                        countQuestions = quiz.value.countQuestions,
                                        countOfAnswers = quiz.value.countOfAnswers,
                                        authorNickname = quiz.value.authorNickname,
                                        countOfPassing = quiz.value.countOfPassing
                                    )
                                )
                                quiz.value = QuizResponse(
                                    id = quiz.value.id,
                                    createdAt = quiz.value.createdAt,
                                    title = title.value,
                                    type = quiz.value.type,
                                    description = quiz.value.description,
                                    countQuestions = quiz.value.countQuestions,
                                    countOfAnswers = quiz.value.countOfAnswers,
                                    authorNickname = quiz.value.authorNickname,
                                    countOfPassing = quiz.value.countOfPassing
                                )
                            },
                            colors = IconButtonDefaults.iconButtonColors(containerColor = if (title.value == quiz.value.title) Color.LightGray else Pink200)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.check),
                                contentDescription = "check",
                                tint = Color.White
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.85F)
                        ) {
                            QuizMarketTextField(variable = description, label = "description")
                        }
                        IconButton(
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 7.dp),
                            onClick = {
                                viewModel.updateQuizInfo(
                                    token,
                                    quiz.value.id,
                                    QuizRequest(
                                        title = quiz.value.title,
                                        type = quiz.value.type,
                                        description = description.value,
                                        countQuestions = quiz.value.countQuestions,
                                        countOfAnswers = quiz.value.countOfAnswers,
                                        authorNickname = quiz.value.authorNickname,
                                        countOfPassing = quiz.value.countOfPassing
                                    )
                                )
                                quiz.value = QuizResponse(
                                    id = quiz.value.id,
                                    createdAt = quiz.value.createdAt,
                                    title = quiz.value.title,
                                    type = quiz.value.type,
                                    description = description.value,
                                    countQuestions = quiz.value.countQuestions,
                                    countOfAnswers = quiz.value.countOfAnswers,
                                    authorNickname = quiz.value.authorNickname,
                                    countOfPassing = quiz.value.countOfPassing
                                )
                            },
                            colors = IconButtonDefaults.iconButtonColors(containerColor = if (description.value == quiz.value.description) Color.LightGray else Pink200)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.check),
                                contentDescription = "check",
                                tint = Color.White
                            )
                        }
                    }
                    Text("Users who passed:", fontSize = 20.sp, color = Color.DarkGray)
                    LazyColumn {
                        items(users.size) { index ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth().padding(vertical = 5.dp),
                                onClick = {
                                    user.value = users[index]
                                    navController.navigate("AllAnswersScreen")
                                }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                ) {
                                    Text(
                                        text = users[index].name,
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
    }
}