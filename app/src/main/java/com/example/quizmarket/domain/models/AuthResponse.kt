package com.example.quizmarket.domain.models

data class AuthResponse(
    val accessToken: String = "",
    val userId: Long = -1
)