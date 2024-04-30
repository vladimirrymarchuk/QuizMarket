package com.example.quizmarket.ui.аuthentication.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizmarket.data.repositories.LoginRepository
import com.example.quizmarket.domain.models.AuthRequest
import com.example.quizmarket.domain.models.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    private val _authResponse = MutableStateFlow(AuthResponse())
    val authResponse: StateFlow<AuthResponse> = _authResponse

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _authResponse.value = repository.login(AuthRequest(email, password))
        }
    }
}
