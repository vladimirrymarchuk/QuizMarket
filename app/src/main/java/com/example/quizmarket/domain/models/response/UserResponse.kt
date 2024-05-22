package com.example.quizmarket.domain.models.response

data class UserResponse(
    val id: Long = 0,
    val createdAt: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val role: String = "USER"
)