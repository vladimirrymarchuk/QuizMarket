package com.example.quizmarket.domain.models.response

import java.io.Serializable

data class QuizResponse(
    val id: Long = 0,
    val createdAt: String = "",
    var title: String = "",
    val type: String = "",
    val description: String = "",
    val countQuestions: Long = 0,
    val countOfAnswers: Long = 0,
    val authorNickname: String = "",
    val countOfPassing: String = ""
) : Serializable
