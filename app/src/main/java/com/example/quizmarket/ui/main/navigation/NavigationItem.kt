package com.example.quizmarket.ui.main.navigation

import com.example.quizmarket.R

sealed class NavigationItem(val title: String, val iconId: Int, val route: String) {
    object Home : NavigationItem("Home", R.drawable.home, "HomeScreen")
    object Settings : NavigationItem("Settings", R.drawable.settings, "SettingsScreen")
    object MyQuiz : NavigationItem("My quizzes", R.drawable.saved, "MyQuizScreen")
    object Quiz : NavigationItem("Quiz", R.drawable.test, "QuizPreview")
    object Search : NavigationItem("Search", R.drawable.search, "SearchPreview")
    object Builder : NavigationItem("Builder", R.drawable.builder, "BuilderPreview")
    object LogOut : NavigationItem("Log out", R.drawable.logout, "")
}