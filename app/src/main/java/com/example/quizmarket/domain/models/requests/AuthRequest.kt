package com.example.quizmarket.domain.models.requests

data class AuthRequest(
    val email: String = "",
    val password: String = ""
)