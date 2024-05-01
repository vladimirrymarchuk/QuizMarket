package com.example.quizmarket.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.quizmarket.R
import com.example.quizmarket.domain.models.UserRequest
import com.example.quizmarket.domain.models.UserResponse
import com.example.quizmarket.ui.composable.PasswordField
import com.example.quizmarket.ui.composable.QuizMarketTextField
import com.example.quizmarket.ui.theme.Pink200
import com.example.quizmarket.ui.theme.QuizMarketTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class SettingsActivity : ComponentActivity() {
    private lateinit var viewModel: SettingsViewModel
    private var userId: Long = 0
    private lateinit var token: String
    private lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMarketTheme {
                viewModel = koinViewModel()
                token =
                    getSharedPreferences("accessToken", MODE_PRIVATE).getString("accessToken", "")
                        .toString()
                userId = getSharedPreferences("userId", MODE_PRIVATE).getLong("userId", 0)
                password = getSharedPreferences("password", MODE_PRIVATE).getString("password", "")
                    .toString()
                viewModel.loadUser(userId)
                val isLoading by viewModel.isLoading.collectAsState()
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                } else {
                    SettingsScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)


    @Composable
    private fun SettingsScreen() {
        val user by viewModel.user.collectAsState()
        val username = remember {
            mutableStateOf(user.name)
        }
        val email = remember {
            mutableStateOf(user.email)
        }
        val password = remember {
            mutableStateOf(password)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Settings") },
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
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.padding(5.dp).fillMaxWidth(0.85F)
                    ){
                        QuizMarketTextField(variable = username, label = "username")
                    }
                    IconButton(
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 7.dp),
                        onClick = {
                            viewModel.updateUser(
                                token,
                                userId,
                                UserRequest(
                                    username.value,
                                    user.email,
                                    this@SettingsActivity.password,
                                    user.role
                                )
                            )
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = if (username.value == user.name) Color.LightGray else Pink200)
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
                        modifier = Modifier.padding(5.dp).fillMaxWidth(0.85F)
                    ){
                        QuizMarketTextField(variable = email, label = "email")
                    }
                    IconButton(
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 7.dp),
                        onClick = {
                            viewModel.updateUser(
                                token,
                                userId,
                                UserRequest(
                                    user.name,
                                    email.value,
                                    this@SettingsActivity.password,
                                    user.role
                                )
                            )
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = if (email.value == user.email) Color.LightGray else Pink200)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.check),
                            contentDescription = "check",
                            tint = Color.White
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier.padding(5.dp).fillMaxWidth(0.85F)
                    ){
                        PasswordField(variable = password)
                    }
                    IconButton(
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 7.dp),
                        onClick = {
                            viewModel.updateUser(
                                token,
                                userId,
                                UserRequest(
                                    user.name,
                                    user.email,
                                    password.value,
                                    user.role
                                )
                            )
                            getSharedPreferences("password", MODE_PRIVATE).edit().putString("password", password.value).apply()
                            this@SettingsActivity.password =
                                getSharedPreferences("password", MODE_PRIVATE).getString("password", "")
                                    .toString()
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (password.value == this@SettingsActivity.password) Color.LightGray else Pink200
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.check),
                            contentDescription = "check",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

