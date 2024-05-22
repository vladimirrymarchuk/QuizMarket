package com.example.quizmarket.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizmarket.data.repositories.SettingsRepository
import com.example.quizmarket.domain.models.requests.UserRequest
import com.example.quizmarket.domain.models.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository): ViewModel() {
    private val _user = MutableStateFlow<UserResponse>(UserResponse())
    val user: StateFlow<UserResponse> = _user

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadUser(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _user.value = repository.loadUser(id)
            _isLoading.value = false
        }
    }

    fun updateUser(token: String, id: Long, updateUser: UserRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = repository.updateUser(token, id, updateUser)
        }
    }
}