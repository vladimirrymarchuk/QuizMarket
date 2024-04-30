package com.example.quizmarket.data.repositories

import com.example.quizmarket.data.api.ApiService

class MainRepository(private val apiService: ApiService) {

    suspend fun getListOfQuizzes(token: String) = apiService.getAllQuizzes(token)

}