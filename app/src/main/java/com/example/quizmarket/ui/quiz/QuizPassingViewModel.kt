package com.example.quizmarket.ui.quiz

import androidx.lifecycle.ViewModel
import com.example.quizmarket.data.repositories.QuizRepository
import com.example.quizmarket.domain.models.QuizResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuizPassingViewModel(repository: QuizRepository) : ViewModel() {
    private val _quiz = MutableStateFlow(QuizResponse())
    val quiz: StateFlow<QuizResponse> = _quiz

    fun setQuiz(quiz: QuizResponse) {
        _quiz.value = quiz
    }
}