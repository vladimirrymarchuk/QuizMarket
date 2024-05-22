package com.example.quizmarket.domain.models.response

data class AuthResponse(
    val accessToken: String = "",
    val userId: Long = -1
)