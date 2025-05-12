package com.example.intellireview_research_paper.data.mapper

import UserRepository
import com.example.intellireview_research_paper.data.remote.UserApi
import com.example.intellireview_research_paper.model.usermodel

class UserRepositoryImpl(private val api: UserApi) : UserRepository {

    override suspend fun Signup(
        name: String,
        email: String,
        password: String,  // Removed redundant toString()
        country: String,
        role: String
    ): usermodel {
        val response = api.signup(
            name = name,
            email = email,
            password = password,  // Directly use String
            country = country,
            role = role
        )

        if (response.isSuccessful) {
            val signupResponse = response.body()
                ?: throw Exception("Signup failed: Empty response body")

            return signupResponse.user ?: throw Exception("Signup failed: No user data in response")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Signup failed: $errorBody")
        }
    }
    // Login method implementation
    override suspend fun Login(email: String, password: String): usermodel {
        val response = api.login(email, password)
        if (response.isSuccessful) {
            val loginResponse = response.body()
            // Extract the nested "user" object
            return loginResponse?.user ?: throw Exception("Login failed: No user data")
        } else {
            throw Exception("Login failed: ${response.errorBody()?.string()}")
        }
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
