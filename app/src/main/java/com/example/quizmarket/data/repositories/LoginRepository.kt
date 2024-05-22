package com.example.quizmarket.data.repositories

import com.example.quizmarket.data.api.ApiService
import com.example.quizmarket.domain.models.requests.AuthRequest
import com.example.quizmarket.domain.models.requests.UserRequest

class LoginRepository(private val apiService: ApiService) {
    suspend fun login(body: AuthRequest) = apiService.login(body)
    suspend fun registration(body: UserRequest) = apiService.registration(body)
}


