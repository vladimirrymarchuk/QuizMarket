package com.example.quizmarket.domain.models

data class RegistrationResponse(
    val id: Long,
    val createdAt: String,
    val name: String,
    val email: String,
    val password: String,
    val role: String
)