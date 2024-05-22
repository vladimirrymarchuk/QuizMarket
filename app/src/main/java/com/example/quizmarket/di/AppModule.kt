package com.example.quizmarket.di

import com.example.quizmarket.data.api.ApiService
import com.example.quizmarket.data.repositories.LoginRepository
import com.example.quizmarket.data.repositories.MainRepository
import com.example.quizmarket.data.repositories.MyQuizzesRepository
import com.example.quizmarket.data.repositories.QuizConstructorRepository
import com.example.quizmarket.data.repositories.QuizPassingRepository
import com.example.quizmarket.data.repositories.SettingsRepository
import com.example.quizmarket.ui.main.MainViewModel
import com.example.quizmarket.ui.myQuizzes.MyQuizzesViewModel
import com.example.quizmarket.ui.quiz.create.QuizConstructorViewModel
import com.example.quizmarket.ui.quiz.passing.QuizPassingViewModel
import com.example.quizmarket.ui.settings.SettingsViewModel
import com.example.quizmarket.ui.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



val appModule = module {
    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.68:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Repositories
    single { LoginRepository(get()) }
    single { MainRepository(get()) }
    single { QuizPassingRepository(get()) }
    single { SettingsRepository(get()) }
    single { QuizConstructorRepository(get()) }
    single { MyQuizzesRepository(get()) }

    // ViewModels
    viewModel { AuthViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { QuizPassingViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { QuizConstructorViewModel(get()) }
    viewModel { MyQuizzesViewModel(get())}
}