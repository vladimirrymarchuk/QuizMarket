package com.example.quizmarket.domain.models

data class AnswerRequest(
    val answerContent: List<String>,
    val questionId: Long
)