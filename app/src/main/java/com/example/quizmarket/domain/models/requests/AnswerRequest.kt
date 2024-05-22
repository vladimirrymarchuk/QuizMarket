package com.example.quizmarket.domain.models.requests

data class AnswerRequest(
    val answerContent: List<String>,
    val questionId: Long
)