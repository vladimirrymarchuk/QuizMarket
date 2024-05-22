package com.example.quizmarket.domain.models.requests

data class UserRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String = "USER"
)