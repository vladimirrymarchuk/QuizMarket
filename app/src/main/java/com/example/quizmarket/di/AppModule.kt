package com.example.quizmarket.di

import com.example.quizmarket.data.api.ApiService
import com.example.quizmarket.data.repositories.LoginRepository
import com.example.quizmarket.data.repositories.MainRepository
import com.example.quizmarket.data.repositories.QuizPassingRepository
import com.example.quizmarket.data.repositories.RegistrationRepository
import com.example.quizmarket.ui.main.MainViewModel
import com.example.quizmarket.ui.quiz.QuizPassingViewModel
import com.example.quizmarket.ui.аuthentication.login.LoginViewModel
import com.example.quizmarket.ui.аuthentication.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



val appModule = module {
    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.68:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Repositories
    single { RegistrationRepository(get()) }
    single { LoginRepository(get()) }
    single { MainRepository(get()) }
    single { QuizPassingRepository(get()) }

    // ViewModels
    viewModel { RegistrationViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { QuizPassingViewModel(get()) }
}