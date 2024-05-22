package com.example.quizmarket.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizmarket.data.repositories.LoginRepository
import com.example.quizmarket.domain.models.requests.AuthRequest
import com.example.quizmarket.domain.models.requests.UserRequest
import com.example.quizmarket.domain.models.response.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: LoginRepository) : ViewModel() {
    var password: String? = null

    private val _authResponse = MutableStateFlow(AuthResponse())
    val authResponse: StateFlow<AuthResponse> = _authResponse

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _authResponse.value = repository.login(AuthRequest(email, password))
            Log.d("jwt", authResponse.value.accessToken)
        }
    }

    fun registration(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.registration(UserRequest(name, email, password))
        }
    }
}
