package com.example.quizmarket.data.repositories

import com.example.quizmarket.data.api.ApiService
import com.example.quizmarket.domain.models.UserRequest

class SettingsRepository(private val apiService: ApiService) {
    suspend fun loadUser(id: Long) = apiService.loadUser(id)

    suspend fun updateUser(token: String, id: Long, updateUser: UserRequest) = apiService.updateUser(token, id, updateUser)
}
