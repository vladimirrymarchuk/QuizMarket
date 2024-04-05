package com.example.quizmarket.data.repositories

import com.example.quizmarket.data.api.ApiService

class MainRepository(private val apiService: ApiService) {

    suspend fun getListOfQuizes() = apiService.getAllQuizes()

}