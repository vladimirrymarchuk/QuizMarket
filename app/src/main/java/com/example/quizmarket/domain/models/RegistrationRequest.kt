package com.example.quizmarket.domain.models

data class RegistrationRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String = "USER"
)