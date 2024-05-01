package com.example.quizmarket.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.quizmarket.domain.models.QuizResponse
import com.example.quizmarket.ui.main.items.NavigationItem
import com.example.quizmarket.ui.quiz.create.QuizConstructorActivity
import com.example.quizmarket.ui.quiz.passing.QuizPassingActivity
import com.example.quizmarket.ui.settings.SettingsActivity
import com.example.quizmarket.ui.theme.Pink200
import com.example.quizmarket.ui.theme.QuizMarketTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMarketTheme {
                viewModel = koinViewModel()
                getSharedPreferences(
                    "accessToken",
                    MODE_PRIVATE
                ).getString("accessToken", null)?.let {
                    viewModel.loadQuizzes(it)
                }
                val navController = rememberNavController()
                val quiz = remember { mutableStateOf(QuizResponse()) }
                NavGraph(navHostController = navController, quiz = quiz)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(navController: NavController, quiz: MutableState<QuizResponse>) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()


        Drawer(
            drawerState = drawerState,
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = NavigationItem.Home.title, color = Color.White) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink200),
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.White
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    Intent(
                                        applicationContext,
                                        QuizConstructorActivity::class.java
                                    ).also { intent ->
                                        startActivity(intent)
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = NavigationItem.Builder.iconId),
                                    contentDescription = NavigationItem.Builder.title,
                                    tint = Color.White
                                )
                            }
                            IconButton(
                                onClick = {

                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = NavigationItem.Search.iconId),
                                    contentDescription = NavigationItem.Search.title,
                                    tint = Color.White
                                )
                            }

                        },
                    )
                },
            ) {
                Box(
                    modifier = Modifier.padding(it)
                ) {
                    HomeScreen(navController = navController, quiz = quiz)
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeScreen(
        navController: NavController,
        quiz: MutableState<QuizResponse>
    ) {
        val quizes by viewModel.quizes.collectAsState()
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
                items(quizes.size) { i ->
                    quizes[i].let { item ->
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

    @Composable
    fun ProfileScreen() {
        /*TODO*/
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun QuizPreviewScreen(
        quiz: MutableState<QuizResponse>,
        navController: NavController
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = NavigationItem.Quiz.title, color = Color.White) },
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
                            text = "Count question: ${quiz.value.countQuestions}",
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
                                applicationContext,
                                QuizPassingActivity::class.java
                            ).also { intent ->
                                intent.putExtra("quiz", quiz.value)
                                startActivity(intent)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Pink200,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Pass quiz", fontSize = 20.sp)
                    }
                }
            }
        }
    }

    @Composable
    fun Drawer(
        drawerState: DrawerState,
        content: @Composable () -> Unit
    ) {
        val scope = rememberCoroutineScope()
        val items = listOf(
            NavigationItem.Saved,
            NavigationItem.Settings,
            NavigationItem.LogOut
        )

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Pink200),
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween
                    ) {
                        Column {
                            IconButton(onClick = { /*TODO*/ }) {

                            }
                        }
                    }
                    items[0].let { item ->
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.iconId),
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title) },
                            selected = false,
                            onClick = { scope.launch { drawerState.close() } },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.White,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                    }
                    items[1].let { item ->
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.iconId),
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title) },
                            selected = false,
                            onClick = {
                                Intent(
                                    applicationContext,
                                    SettingsActivity::class.java
                                ).also { intent ->
                                    startActivity(intent)
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.White,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                    }
                    items[2].let { item ->
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.iconId),
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title) },
                            selected = false,
                            onClick = {
                                getSharedPreferences("accessToken", MODE_PRIVATE).edit()
                                    .putString("accessToken", "").apply()
                                finish()
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.White,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                    }
                }
            },
            content = {
                content()
            }
        )
    }

    @Composable
    fun NavGraph(
        navHostController: NavHostController,
        quiz: MutableState<QuizResponse>
    ) {
        NavHost(navController = navHostController, startDestination = NavigationItem.Home.route) {
            composable(NavigationItem.Home.route) {
                MainScreen(navController = navHostController, quiz = quiz)
            }
            composable(NavigationItem.Quiz.route) {
                QuizPreviewScreen(navController = navHostController, quiz = quiz)
            }
        }
    }
}
