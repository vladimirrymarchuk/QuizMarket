package com.example.quizmarket.ui.myQuizzes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizmarket.data.repositories.MyQuizzesRepository
import com.example.quizmarket.domain.models.response.AnswerResponse
import com.example.quizmarket.domain.models.requests.QuizRequest
import com.example.quizmarket.domain.models.response.QuizResponse
import com.example.quizmarket.domain.models.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyQuizzesViewModel(private val repository: MyQuizzesRepository) : ViewModel() {
    private val _user = MutableStateFlow(UserResponse())
    val user: StateFlow<UserResponse> = _user

    private val _quizzes = MutableStateFlow<List<QuizResponse>>(emptyList())
    val quizzes: StateFlow<List<QuizResponse>> = _quizzes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isDeleting = MutableStateFlow(false)
    val isDeleting: StateFlow<Boolean> = _isDeleting

    private val _users = MutableStateFlow<List<UserResponse>>(emptyList())
    val users: StateFlow<List<UserResponse>> = _users

    private val _answers = MutableStateFlow<List<AnswerResponse>>(emptyList())
    val answers: StateFlow<List<AnswerResponse>> = _answers

    fun loadQuizzes(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _quizzes.value =
                repository.getListOfQuizzes(token).sortedByDescending { it.countOfPassing.toLong() }
                    .filter { it.authorNickname == _user.value.name }
            Log.i("allQuizzes", _quizzes.value.toString())
            _isLoading.value = false
        }
    }

    fun loadUser(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _user.value = repository.loadUser(id)
            _isLoading.value = false
        }
    }

    fun deleteQuiz(token: String, quiz: QuizResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteQuiz(
                token,
                quiz.id,
                QuizRequest(
                    title = quiz.title,
                    type = quiz.type,
                    description = quiz.description,
                    countQuestions = quiz.countQuestions,
                    countOfAnswers = quiz.countOfAnswers,
                    authorNickname = quiz.authorNickname,
                    countOfPassing = quiz.countOfPassing
                )
            )
        }
    }

    fun updateQuizInfo(token: String, quizId: Long, updateQuiz: QuizRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateQuizInfo(token, quizId, updateQuiz)
        }
    }

    fun allWhoPassed(token: String, quizId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _users.value = repository.allWhoPassed(token, quizId)
        }
    }

    fun getAllQuizAnswersByQuizId(token: String, quizId: Long, userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _answers.value = repository.getAllQuizAnswersByQuizId(token, quizId, userId)
        }
    }
}