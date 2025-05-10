package com.example.intellireview_research_paper.data.mapper

import UserRepository
import com.example.intellireview_research_paper.data.remote.UserApi
import com.example.intellireview_research_paper.model.usermodel

class UserRepositoryImpl(private val api: UserApi) : UserRepository {

    // Signup method implementation
    override suspend fun Signup(
        name: String,
        email: String,
        password: String,
        country: String,
        role: String
    ): usermodel {
        val response = api.signup(
            name = name,
            email = email,
            password = password.toString(),
            country = country,
            role = role
        )
        return response.body() ?: throw Exception("Signup failed: ${response.errorBody()?.string()}")
    }

    // Login method implementation
    override suspend fun Login(
        email: String,
        password: String
    ): usermodel {
        val response = api.login(email, password.toString())
        return response.body() ?: throw Exception("Login failed: ${response.errorBody()?.string()}")
    }

    // Logout method implementation
    override suspend fun Logout() {
        val response = api.logout()
        if (!response.isSuccessful) {
            throw Exception("Logout failed: ${response.errorBody()?.string()}")
        }
    }

    // Profile update method implementation
    override suspend fun updateprofile(ProfilePic: String): usermodel {
        val response = api.updateProfile(ProfilePic)
        return response.body() ?: throw Exception("Update failed: ${response.errorBody()?.string()}")
    }
}
