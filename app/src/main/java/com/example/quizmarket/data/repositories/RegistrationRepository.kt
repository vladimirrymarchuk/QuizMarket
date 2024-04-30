package com.example.quizmarket.data.repositories

import com.example.quizmarket.data.api.ApiService
import com.example.quizmarket.domain.models.UserRequest

class RegistrationRepository(private val apiService: ApiService) {
    suspend fun registration(body: UserRequest) = apiService.registration(body)
}