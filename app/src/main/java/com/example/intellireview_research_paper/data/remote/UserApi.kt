package com.example.intellireview_research_paper.data.remote


import LoginResponse
import SignupResponse
import com.example.intellireview_research_paper.model.usermodel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {

    @FormUrlEncoded
    @POST("auth/signup")
    suspend fun signup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("country") country: String,
        @Field("role") role: String
    ): Response<SignupResponse>  // Changed to SignupResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @POST("/auth/logout")
    suspend fun logout(): Response<Void>

    @FormUrlEncoded
    @PUT("/auth/Updateprofile")
    suspend fun updateProfile(
        @Field("profilePic") profilePic: String
    ): Response<usermodel>
}



