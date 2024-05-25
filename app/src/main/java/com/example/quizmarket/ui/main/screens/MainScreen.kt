package com.example.quizmarket.ui.main.screens

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizmarket.R
import com.example.quizmarket.domain.models.response.QuizResponse
import com.example.quizmarket.ui.main.MainActivity
import com.example.quizmarket.ui.main.MainViewModel
import com.example.quizmarket.ui.main.navigation.Drawer
import com.example.quizmarket.ui.main.navigation.NavigationItem
import com.example.quizmarket.ui.quiz.create.QuizConstructorActivity
import com.example.quizmarket.ui.theme.Pinklight
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    quiz: MutableState<QuizResponse>,
    viewModel: MainViewModel,
    token: String
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val isSearching = remember {
        mutableStateOf(false)
    }
    val search = remember {
        mutableStateOf("")
    }
    val activity = LocalContext.current as MainActivity
    val quizzes by viewModel.quizzes.collectAsState()

    Drawer(
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = NavigationItem.Home.title, color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Pinklight),
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
                        if (!isSearching.value) {
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
                            IconButton(
                                onClick = {
                                    Intent(
                                        activity,
                                        QuizConstructorActivity::class.java
                                    ).also { intent ->
                                        activity.startActivity(intent)
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = NavigationItem.Builder.iconId),
                                    contentDescription = NavigationItem.Builder.title,
                                    tint = Color.White
                                )
                            }
                        } else {
                            Box(modifier = Modifier.fillMaxWidth(0.75f)) {
                                TextField(
                                    value = search.value,
                                    shape = RoundedCornerShape(15.dp),
                                    textStyle = TextStyle(
                                        fontSize = 20.sp,
                                        color = Color.DarkGray
                                    ),
                                    onValueChange = { newText -> search.value = newText },
                                    singleLine = true,
                                    colors = TextFieldDefaults.colors(
                                        focusedLabelColor = Color.DarkGray,
                                        disabledLabelColor = Color.Gray,
                                        unfocusedIndicatorColor = Pinklight,
                                        focusedIndicatorColor = Pinklight
                                    ),
                                    placeholder = { Text(text = "Search") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }
                        IconButton(
                            onClick = {
                                isSearching.value = !isSearching.value
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
                if (!isSearching.value) {
                    ListQuizzesScreen(navController = navController, quiz = quiz, viewModel = viewModel)
                } else {
                    val item = quizzes.find { it.title == search.value }
                    if (item != null) {
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
                                modifier = Modifier.fillMaxWidth(),
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
}