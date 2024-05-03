package com.example.quizmarket.data.repositories

import com.example.quizmarket.data.api.ApiService
import com.example.quizmarket.domain.models.QuizRequest

class QuizConstructorRepository(private val apiService: ApiService) {
    suspend fun createQuiz(token: String, newQuiz: QuizRequest) = apiService.createQuiz(token, newQuiz)

    suspend fun loadUser(userId: Long) = apiService.loadUser(userId)

    suspend fun addQuestions(token: String, quizId: Long, body: List<String>) = apiService.addQuestion(token, quizId, body)

    suspend fun addAnswers(token: String, quizId: Long, body: List<String>) = apiService.addAnswers(token, quizId, body)

}