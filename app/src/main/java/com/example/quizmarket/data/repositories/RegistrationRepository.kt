package com.example.quizmarket.data.repositories

import com.example.quizmarket.data.api.ApiService
import com.example.quizmarket.domain.models.RegistrationRequest

class RegistrationRepository(private val apiService: ApiService) {
    suspend fun registration(body: RegistrationRequest) = apiService.registration(body)
}