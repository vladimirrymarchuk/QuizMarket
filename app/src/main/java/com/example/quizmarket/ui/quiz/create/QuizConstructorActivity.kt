package com.example.quizmarket.ui.quiz.create

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.quizmarket.R
import com.example.quizmarket.ui.theme.Pink200
import com.example.quizmarket.ui.theme.QuizMarketTheme
import org.koin.androidx.compose.koinViewModel

class QuizConstructorActivity : ComponentActivity() {
    private lateinit var viewModel: QuizConstructorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMarketTheme {
                viewModel = koinViewModel()
                CreateQuizScreen()
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CreateQuizScreen() {
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
                        containerColor = Pink200,
                        navigationIconContentColor = Color.White,
                        titleContentColor = Color.White
                    )
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                QuizInfoScreen()
            }
        }
    }

    @Composable
    private fun QuizInfoScreen() {
        Column(modifier = Modifier.fillMaxSize()) {

        }
    }
}