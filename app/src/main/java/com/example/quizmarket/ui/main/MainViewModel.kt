package com.example.quizmarket.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizmarket.data.repositories.MainRepository
import com.example.quizmarket.domain.models.response.QuizResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _quizzes = MutableStateFlow<List<QuizResponse>>(emptyList())
    val quizzes: StateFlow<List<QuizResponse>> = _quizzes


    fun loadQuizzes(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _quizzes.value = repository.getListOfQuizzes(token).sortedByDescending { it.countOfPassing.toLong() }
            Log.i("quizzes", _quizzes.value.toString())
            _isLoading.value = false
        }
    }

    fun loadUser(token: String) {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}