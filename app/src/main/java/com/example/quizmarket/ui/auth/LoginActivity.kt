package com.example.quizmarket.ui.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.Intent
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.quizmarket.ui.main.MainActivity
import com.example.quizmarket.ui.theme.QuizMarketTheme
import com.example.quizmarket.ui.auth.screens.LoginScreen
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel



class LoginActivity : ComponentActivity() {
    private val viewModel by viewModel<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMarketTheme {
                tryGetJWT()
                LoginScreen()
                initSubscribe()
            }
        }
    }

    private fun initSubscribe() {
        Log.i("dslsfa;j2", "slkdfa2")
        lifecycleScope.launch {
            viewModel.authResponse.collect { authResponse ->
                Log.i("dslsfa;j", "slkdfa")
                if (authResponse.accessToken != "" && "Ошибка" !in authResponse.accessToken) {
                    Log.d("id", authResponse.userId.toString())
                    saveJWT(authResponse.accessToken, authResponse.userId)
                    Intent(applicationContext, MainActivity::class.java).also { intent -> startActivity(intent) }
                    finish()
                }
            }
        }
    }


    fun tryGetJWT() {
        val accessToken = getSharedPreferences("accessToken", MODE_PRIVATE).getString("accessToken", "") ?: return
        if (accessToken == "" || "Ошибка" in accessToken) return
        Intent(applicationContext, MainActivity::class.java).also { intent -> startActivity(intent) }
        finish()
    }

    fun saveJWT(accessToken: String, userId: Long) {
        getSharedPreferences("accessToken", MODE_PRIVATE).edit().putString("accessToken", accessToken).apply()
        getSharedPreferences("userId", MODE_PRIVATE).edit().putLong("userId", userId).apply()
        getSharedPreferences("password", MODE_PRIVATE).edit().putString("password", viewModel.password).apply()
    }
}