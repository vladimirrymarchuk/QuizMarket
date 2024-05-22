package com.example.quizmarket.data.repositories

import com.example.quizmarket.data.api.ApiService
import com.example.quizmarket.domain.models.requests.QuizRequest

class MyQuizzesRepository(private val apiService: ApiService) {
    suspend fun loadUser(id: Long) = apiService.loadUser(id)
    suspend fun getListOfQuizzes(token: String) = apiService.getAllQuizzes(token)
    suspend fun updateQuizInfo(token: String, quizId: Long, body: QuizRequest) = apiService.updateQuizInfo(token, quizId, body)
    suspend fun deleteQuiz(token: String, quizId: Long, quiz: QuizRequest) = apiService.deleteQuiz(token, quizId, quiz)

    suspend fun allWhoPassed(token: String, quizId: Long) = apiService.allWhoPassed(token, quizId)

    suspend fun getAllQuizAnswersByQuizId(token: String, quizId: Long, userId: Long) = apiService.getAllQuizAnswersByQuizId(token, quizId, userId)
}