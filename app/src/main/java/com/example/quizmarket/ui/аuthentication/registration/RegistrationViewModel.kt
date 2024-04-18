package com.example.quizmarket.ui.аuthentication.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizmarket.data.repositories.MainRepository
import com.example.quizmarket.data.repositories.RegistrationRepository
import com.example.quizmarket.domain.models.RegistrationRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel(private val repository: RegistrationRepository) : ViewModel() {
    fun registration(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.registration(RegistrationRequest(name, email, password))
        }
    }
}