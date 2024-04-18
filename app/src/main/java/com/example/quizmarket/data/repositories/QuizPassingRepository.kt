package com.example.quizmarket.data.repositories

import com.example.quizmarket.data.api.ApiService
import com.example.quizmarket.domain.models.AnswerRequest

class QuizPassingRepository(private val apiService: ApiService) {

    suspend fun passQuiz(
        token: String,
        body: List<AnswerRequest>,
        quizId: Long, userId: Long
    ) = apiService.passQuiz(token, body, quizId, userId)

    suspend fun getAllQuizQuestionsByQuizId(token: String, quizId: Long) = apiService.getAllQuizQuestionsByQuizId(token, quizId)
}