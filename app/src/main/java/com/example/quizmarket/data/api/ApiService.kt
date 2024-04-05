package com.example.quizmarket.data.api

import com.example.quizmarket.domain.models.AnswerRequest
import com.example.quizmarket.domain.models.QuizResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("quiz/all")
    suspend fun getAllQuizes(): List<QuizResponse>

    @POST
    suspend fun passQuiz(@Body body: List<AnswerRequest>): List<AnswerRequest>

}