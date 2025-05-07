package com.example.intellireview_research_paper.data.remote


import com.example.intellireview_research_paper.model.usermodel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface UserApi {

    @FormUrlEncoded
    @POST("/auth/signup")
    suspend fun signup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("country") country: String,
        @Field("role") role: String
    ): Response<usermodel>

    @FormUrlEncoded
    @POST("/auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<usermodel>

    @POST("/auth/logout")
    suspend fun logout(): Response<Void>

    @FormUrlEncoded
    @PUT("/auth/Updateprofile")
    suspend fun updateProfile(
        @Field("profilePic") profilePic: String
    ): Response<usermodel>
}



object UserApiClient {

    private const val BASE_URL = "https://localhost:3500/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: UserApi = retrofit.create(UserApi::class.java)


    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}


