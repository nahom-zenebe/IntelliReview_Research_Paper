package com.example.intellireview_research_paper.data.remote

import com.example.intellireview_research_paper.model.notificationmodel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface NotificationApi {

    @GET("/getnotifications")
    suspend fun getNotifications(): Response<List<notificationmodel>>

    @FormUrlEncoded
    @POST("/createnotifications")
    suspend fun createNotification(
        @Field("title") title: String,
        @Field("message") message: String
    ): Response<notificationmodel>

    @FormUrlEncoded
    @PUT("/getnotifications/{notificationId}")
    suspend fun editNotification(
        @Path("notificationId") notificationId: String,
        @Field("title") title: String,
        @Field("message") message: String
    ): Response<notificationmodel>
}


object NotificationApiClient {

    private const val BASE_URL = "https://localhost:3500/api/"  // Adjust base URL as necessary

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(createOkHttpClient())  // Optional OkHttpClient setup for SSL handling
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService:NotificationApi = retrofit.create(NotificationApi::class.java)

    // Optional: OkHttpClient setup for SSL handling (only if necessary for local dev)
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}

