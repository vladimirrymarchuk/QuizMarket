package com.example.quizmarket.data.api

import com.example.quizmarket.domain.models.AnswerRequest
import com.example.quizmarket.domain.models.AnswerResponse
import com.example.quizmarket.domain.models.AuthRequest
import com.example.quizmarket.domain.models.AuthResponse
import com.example.quizmarket.domain.models.DeleteResponse
import com.example.quizmarket.domain.models.QuestionResponse
import com.example.quizmarket.domain.models.QuizRequest
import com.example.quizmarket.domain.models.QuizResponse
import com.example.quizmarket.domain.models.UserRequest
import com.example.quizmarket.domain.models.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("/api/quiz/all")
    suspend fun getAllQuizzes(@Header("Authorization") token: String): List<QuizResponse>

    @Headers("Content-type: application/json")
    @POST("/api/passQuiz/{quizId}/{userId}")
    suspend fun passQuiz(
        @Header("Authorization") token: String,
        @Body body: List<AnswerRequest>,
        @Path("quizId") quizId: Long,
        @Path("userId") userId: Long
    ): List<AnswerRequest>

    @GET("/api/quiz/allQuizQuestions/{quizId}")
    suspend fun getAllQuizQuestionsByQuizId(
        @Header("Authorization") token: String,
        @Path("quizId") quizId: Long
    ): List<QuestionResponse>

    @POST("/api/auth")
    suspend fun login(@Body body: AuthRequest): AuthResponse

    @POST("/api/reg")
    suspend fun registration(@Body body: UserRequest): UserResponse

    @GET("/api/user/getUser/{userId}")
    suspend fun loadUser(@Path("userId") userId: Long): UserResponse

    @Headers("Content-type: application/json")
    @PUT("/api/user/{userId}")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: Long,
        @Body updateUser: UserRequest
    ): UserResponse

    @Headers("Content-type: application/json")
    @POST("/api/quiz/admin/createQuizInfo")
    suspend fun createQuiz(
        @Header("Authorization") token: String,
        @Body newQuiz: QuizRequest
    ): QuizResponse

    @Headers("Content-type: application/json")
    @POST("/api/quiz/admin/addQuestions/{quizId}")
    suspend fun addQuestion(
        @Header("Authorization") token: String,
        @Path("quizId") quizId: Long,
        @Body body: List<String>
    ): List<QuestionResponse>

    @Headers("Content-type: application/json")
    @POST("/api/quiz/admin/addAnswers/{quizId}")
    suspend fun addAnswers(
        @Header("Authorization") token: String,
        @Path("quizId") quizId: Long,
        @Body body: List<String>
    ): List<AnswerResponse>


    @Headers("Content-type: application/json")
    @PUT("/api/quiz/admin/{quizId}")
    suspend fun updateQuizInfo(
        @Header("Authorization") token: String,
        @Path("quizId") quizId: Long,
        @Body body: QuizRequest
    ): QuizResponse

    @Headers("Content-type: application/json")
    @HTTP(method = "DELETE", path = "/api/quiz/admin/{quizId}", hasBody = true)
    suspend fun deleteQuiz(
        @Header("Authorization") token: String,
        @Path("quizId") quizId: Long,
        @Body body: QuizRequest
    ): DeleteResponse

    @Headers("Content-type: application/json")
    @GET("/api/passQuiz/allWhoPassed/{quizId}")
    suspend fun allWhoPassed(
        @Header("Authorization") token: String,
        @Path("quizId") quizId: Long
    ): List<UserResponse>

    @Headers("Content-type: application/json")
    @GET("/api/quiz/allQuizQuestions/{quizId}/{userId}")
    suspend fun getAllQuizAnswersByQuizId(
        @Header("Authorization") token: String,
        @Path("quizId") quizId: Long,
        @Path("userId") userId: Long
    ): List<AnswerResponse>

}