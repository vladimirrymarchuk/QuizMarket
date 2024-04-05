package com.example.quizmarket.ui.аuthentication.registration

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.quizmarket.ui.theme.QuizMarketTheme
import com.example.quizmarket.ui.аuthentication.login.LoginActivity
import com.example.quizmarket.ui.аuthentication.composable.LoginField
import com.example.quizmarket.ui.аuthentication.composable.LoginButton

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMarketTheme { RegistrationScreen() }
        }
    }

    @Composable
    fun RegistrationScreen() {
        val regData = LinkedHashMap<String, MutableState<String>>().also {
            it["username"] = remember {
                mutableStateOf("")
            }
            it["email"] = remember {
                mutableStateOf("")
            }
            it["password"] = remember {
                mutableStateOf("")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center
        ) {
            regData.forEach { item ->
                LoginField(variable = item.value, label = item.key)
            }
            LoginButton(title = "Registration") {
                Intent(
                    applicationContext,
                    LoginActivity::class.java
                ).also { intent -> startActivity(intent) }
            }
        }
    }
}
