package com.example.quizmarket.ui.quiz.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizmarket.data.repositories.QuizConstructorRepository
import com.example.quizmarket.domain.models.QuizRequest
import com.example.quizmarket.domain.models.QuizResponse
import com.example.quizmarket.domain.models.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizConstructorViewModel(private val repository: QuizConstructorRepository) : ViewModel() {
    private val _user = MutableStateFlow(UserResponse())
    val user: StateFlow<UserResponse> = _user

    private val _quiz = MutableStateFlow(QuizResponse())

    val questions = MutableStateFlow<MutableList<String>>(mutableListOf())
    val answers = MutableStateFlow<MutableList<String>>(mutableListOf())
    fun loadUser(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = repository.loadUser(id)
        }
    }
    fun createQuiz(token: String, newQuiz: QuizRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _quiz.value = repository.createQuiz(token, newQuiz)
        }
    }

    fun addQuestions(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addQuestions(token, _quiz.value.id, questions.value)
        }
    }

    fun addAnswers(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAnswers(token, _quiz.value.id, answers.value)
        }
    }
}