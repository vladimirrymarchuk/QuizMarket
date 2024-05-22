package com.example.quizmarket.ui.quiz.passing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizmarket.data.repositories.QuizPassingRepository
import com.example.quizmarket.domain.models.requests.AnswerRequest
import com.example.quizmarket.domain.models.response.QuestionResponse
import com.example.quizmarket.domain.models.requests.QuizRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizPassingViewModel(private val repository: QuizPassingRepository) : ViewModel() {
    private val _questions = MutableStateFlow<List<QuestionResponse>>(listOf())
    val questions: StateFlow<List<QuestionResponse>> = _questions

    val answers = MutableStateFlow<MutableList<AnswerRequest>>(mutableListOf())

    fun getAllQuizQuestionsByQuizId(token: String, id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _questions.value = repository.getAllQuizQuestionsByQuizId(token = token, quizId = id)
        }
    }

    fun passQuiz(token: String, quizId: Long, userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.passQuiz(token = token, body = answers.value, quizId = quizId, userId = userId)
        }
    }

    fun updateQuizInfo(token: String, quizId: Long, updateQuiz: QuizRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateQuizInfo(token, quizId, updateQuiz)
        }
    }
}