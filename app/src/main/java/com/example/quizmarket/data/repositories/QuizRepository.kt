package com.example.quizmarket.data.repositories

import com.example.quizmarket.data.api.ApiService
import com.example.quizmarket.domain.models.AnswerRequest

class QuizRepository(private val apiService: ApiService) {

    suspend fun passQuiz(body: List<AnswerRequest>) = apiService.passQuiz(body)

}