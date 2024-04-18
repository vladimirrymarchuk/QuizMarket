package com.example.quizmarket.data.api

import com.example.quizmarket.domain.models.AnswerRequest
import com.example.quizmarket.domain.models.AuthRequest
import com.example.quizmarket.domain.models.AuthResponse
import com.example.quizmarket.domain.models.QuestionResponse
import com.example.quizmarket.domain.models.QuizResponse
import com.example.quizmarket.domain.models.RegistrationRequest
import com.example.quizmarket.domain.models.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("api/quiz/all")
    suspend fun getAllQuizes(@Header("Authorization") token: String): List<QuizResponse>

    @Headers("Content-type: application/json")
    @POST("api/passQuiz/{quizId}/{userId}")
    suspend fun passQuiz(
        @Header("Authorization") token: String,
        @Body body: List<AnswerRequest>,
        @Path("quizId") quizId: Long,
        @Path("userId") userId: Long
    ): List<AnswerRequest>

    @GET("api/quiz/allQuizQuestions/{quizId}")
    suspend fun getAllQuizQuestionsByQuizId(
        @Header("Authorization") token: String,
        @Path("quizId") quizId: Long
    ): List<QuestionResponse>

    @POST("api/auth")
    suspend fun login(@Body body: AuthRequest): AuthResponse
    @POST("api/reg")
    suspend fun registration(@Body body: RegistrationRequest): RegistrationResponse
}