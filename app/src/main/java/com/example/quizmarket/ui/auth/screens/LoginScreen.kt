package com.example.quizmarket.ui.auth.screens

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.quizmarket.ui.composable.PasswordField
import com.example.quizmarket.ui.composable.QuizMarketButton
import com.example.quizmarket.ui.composable.QuizMarketTextField
import com.example.quizmarket.ui.auth.AuthViewModel
import com.example.quizmarket.ui.auth.LoginActivity
import com.example.quizmarket.ui.auth.RegistrationActivity
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(viewModel: AuthViewModel = koinViewModel()) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val activity = LocalContext.current as LoginActivity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)){
            QuizMarketTextField(variable = email, label = "email")
        }
        Box(modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)){
            PasswordField(variable = password)
        }
        QuizMarketButton(
            title = "Log in",
            onClick = {
                viewModel.login(email.value, password.value)
                viewModel.password = password.value
            }
        )
        QuizMarketButton(
            title = "Registration",
            onClick = {
                Intent(activity, RegistrationActivity::class.java).also { intent ->
                    activity.startActivity(intent)
                }
            }
        )
    }
}