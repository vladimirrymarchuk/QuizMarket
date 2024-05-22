package com.example.quizmarket.domain.models.requests

data class QuizRequest(
    val title: String,
    val type: String,
    val description: String,
    val countQuestions: Long,
    val countOfAnswers: Long = 0,
    val authorNickname: String,
    val countOfPassing: String = "0"
)