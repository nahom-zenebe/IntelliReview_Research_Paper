package com.example.intellireview_research_paper.data.mapper

import com.example.intellireview_research_paper.data.remote.UserApi
import com.example.intellireview_research_paper.model.usermodel

class UserRepositoryImpl(private val api: UserApi) : UserRepository {

    override suspend fun Signup(
        name: String,
        email: String,
        password: Number,
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

    override suspend fun Login(email: String, password: Number): usermodel {
        val response = api.login(email, password.toString())
        return response.body() ?: throw Exception("Login failed: ${response.errorBody()?.string()}")
    }

    override suspend fun Logout() {
        val response = api.logout()
        if (!response.isSuccessful) {
            throw Exception("Logout failed: ${response.errorBody()?.string()}")
        }
    }

    override suspend fun updateprofile(ProfilePic: String): usermodel {
        val response = api.updateProfile(ProfilePic)
        return response.body() ?: throw Exception("Update failed: ${response.errorBody()?.string()}")
    }
}
