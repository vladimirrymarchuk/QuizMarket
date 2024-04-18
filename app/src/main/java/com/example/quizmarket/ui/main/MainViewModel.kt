package com.example.quizmarket.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizmarket.data.repositories.MainRepository
import com.example.quizmarket.domain.models.QuizResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _quizes = MutableStateFlow<List<QuizResponse>>(emptyList())
    val quizes: StateFlow<List<QuizResponse>> = _quizes


    fun loadQuizes(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _quizes.value = repository.getListOfQuizes(token)
            _quizes.value.sortedBy { it.countOfAnswers }
            _isLoading.value = false
        }
    }
}