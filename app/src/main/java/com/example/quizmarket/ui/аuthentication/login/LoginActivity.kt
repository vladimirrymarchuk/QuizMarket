package com.example.quizmarket.ui.аuthentication.login

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.example.quizmarket.ui.аuthentication.composable.LoginButton
import com.example.quizmarket.ui.аuthentication.composable.LoginField
import com.example.quizmarket.ui.main.MainActivity
import com.example.quizmarket.ui.theme.QuizMarketTheme
import com.example.quizmarket.ui.аuthentication.composable.PasswordField
import com.example.quizmarket.ui.аuthentication.registration.RegistrationActivity
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class LoginActivity : ComponentActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMarketTheme {
                viewModel = koinViewModel()
                tryGetJWT()
                LoginScreen()
                initSubscribe()
            }
        }
    }

    private fun initSubscribe() {
        lifecycleScope.launch {
            viewModel.authResponse.collect { authResponse ->
                if (authResponse.accessToken != "" && "Ошибка" !in authResponse.accessToken) {
                    saveJWT(authResponse.accessToken, authResponse.userId)
                    Intent(applicationContext, MainActivity::class.java).also { intent -> startActivity(intent) }
                    finish()
                }
            }
        }
    }


    private fun tryGetJWT() {
        val accessToken = getSharedPreferences("accessToken", MODE_PRIVATE).getString("accessToken", "") ?: return
        if (accessToken == "" || "Ошибка" in accessToken) return
        Intent(applicationContext, MainActivity::class.java).also { intent -> startActivity(intent) }
        finish()
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveJWT(accessToken: String, userId: Long) {
        getSharedPreferences("accessToken", MODE_PRIVATE).edit().putString("accessToken", accessToken).apply()
        getSharedPreferences("userId", MODE_PRIVATE).edit().putLong("userId", userId).apply()
        getSharedPreferences("password", MODE_PRIVATE).edit().putString("password", password).apply()

    }

    @Composable
    private fun LoginScreen() {
        val email = remember {
            mutableStateOf("")
        }
        val password = remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center
        ) {
            LoginField(variable = email, label = "email")
            PasswordField(variable = password)
            LoginButton(
                title = "Log in",
                onClick = {
                    viewModel.login(email.value, password.value)
                    this@LoginActivity.password = password.value
                }
            )
            LoginButton(
                title = "Registration",
                onClick = {
                    Intent(applicationContext, RegistrationActivity::class.java).also { intent ->
                        startActivity(intent)
                    }
                }
            )
        }
    }
}