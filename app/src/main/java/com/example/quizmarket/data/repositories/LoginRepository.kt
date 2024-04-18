package com.example.quizmarket.data.repositories

import com.example.quizmarket.data.api.ApiService
import com.example.quizmarket.domain.models.AuthRequest

class LoginRepository(private val apiService: ApiService) {
    suspend fun login(body: AuthRequest) = apiService.login(body)
}


