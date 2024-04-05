package com.example.quizmarket.ui.аuthentication.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.quizmarket.ui.аuthentication.composable.LoginButton
import com.example.quizmarket.ui.аuthentication.composable.LoginField
import com.example.quizmarket.ui.main.MainActivity
import com.example.quizmarket.ui.theme.QuizMarketTheme
import com.example.quizmarket.ui.аuthentication.registration.RegistrationActivity

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMarketTheme { LoginScreen() }
        }
    }

    @Composable
    fun LoginScreen() {
        val logData = LinkedHashMap<String, MutableState<String>>().also {
            it["username"] = remember { mutableStateOf("") }
            it["password"] = remember { mutableStateOf("") }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center
        ) {
            logData.forEach { item ->
                LoginField(variable = item.value, label = item.key)
            }
            LoginButton(
                title = "Log in",
                onClick = {
                    Intent(applicationContext, MainActivity::class.java).also { intent ->
                        startActivity(
                            intent
                        )
                    }
                }
            )
            LoginButton(
                title = "Registration",
                onClick = {
                    Intent(applicationContext, RegistrationActivity::class.java).also { intent ->
                        startActivity(
                            intent
                        )
                    }
                }
            )
        }
    }
}