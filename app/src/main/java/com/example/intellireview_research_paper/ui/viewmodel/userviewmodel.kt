package com.example.intellireview_research_paper.ui.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.intellireview_research_paper.data.mapper.UserRepositoryImpl
import com.example.intellireview_research_paper.model.usermodel
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepositoryImpl) : ViewModel() {

    var user by mutableStateOf<usermodel?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set


    fun signup(name: String, email: String, password: Number, country: String, role: String) {
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                user = userRepository.Signup(name, email, password, country, role)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }


    fun login(email: String, password: Number) {
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                user = userRepository.Login(email, password)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }


    fun logout() {
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                userRepository.Logout()
                user = null // Reset user data on logout
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    // Update Profile function
    fun updateProfile(profilePic: String) {
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                user = userRepository.updateprofile(profilePic)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}
