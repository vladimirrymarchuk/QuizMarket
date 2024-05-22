package com.example.quizmarket.data.repositories

import com.example.quizmarket.data.api.ApiService
import com.example.quizmarket.domain.models.requests.UserRequest

class SettingsRepository(private val apiService: ApiService) {
    suspend fun loadUser(userId: Long) = apiService.loadUser(userId)

    suspend fun updateUser(token: String, userId: Long, updateUser: UserRequest) = apiService.updateUser(token, userId, updateUser)
}
