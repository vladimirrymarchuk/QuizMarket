package com.example.quizmarket.ui.аuthentication.registration

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.quizmarket.ui.composable.PasswordField
import com.example.quizmarket.ui.theme.QuizMarketTheme
import com.example.quizmarket.ui.аuthentication.login.LoginActivity
import com.example.quizmarket.ui.composable.QuizMarketTextField
import com.example.quizmarket.ui.composable.QuizMarketButton
import org.koin.androidx.compose.koinViewModel

class RegistrationActivity : ComponentActivity() {
    private lateinit var viewModel: RegistrationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMarketTheme {
                viewModel = koinViewModel()
                RegistrationScreen()
            }
        }
    }

    @Composable
    fun RegistrationScreen() {
        val username = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)){
                QuizMarketTextField(variable = username, label = "username")
            }
            Box(modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)){
                QuizMarketTextField(variable = email, label = "email")
            }
            Box(modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)){
                PasswordField(variable = password)
            }
            QuizMarketButton(title = "Registration") {
                viewModel.registration(username.value, email.value, password.value)
                Intent(applicationContext, LoginActivity::class.java).also { intent -> startActivity(intent) }
                finish()
            }
        }
    }
}
