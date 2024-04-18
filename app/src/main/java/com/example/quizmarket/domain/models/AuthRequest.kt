package com.example.quizmarket.domain.models

data class AuthRequest(
    val email: String = "",
    val password: String = ""
)