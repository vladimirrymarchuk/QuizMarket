package com.example.quizmarket.ui.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizmarket.data.repositories.QuizPassingRepository
import com.example.quizmarket.domain.models.AnswerRequest
import com.example.quizmarket.domain.models.QuestionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizPassingViewModel(private val repository: QuizPassingRepository) : ViewModel() {
    private val _questions = MutableStateFlow<List<QuestionResponse>>(listOf())
    val questions: StateFlow<List<QuestionResponse>> = _questions

    val answers = MutableStateFlow<MutableList<AnswerRequest>>(mutableListOf())

    fun getAllQuizQuestionsByQuizId(token: String, id: Long) {
        viewModelScope.launch {
            _questions.value = repository.getAllQuizQuestionsByQuizId(token = token, quizId = id)
        }
    }

    fun passingQuiz() {
        viewModelScope.launch {
        }
    }
}