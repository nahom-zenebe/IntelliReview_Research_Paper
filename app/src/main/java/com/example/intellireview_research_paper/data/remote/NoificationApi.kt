package com.example.intellireview_research_paper.data.remote

import com.example.intellireview_research_paper.model.notificationmodel
import retrofit2.Response
import retrofit2.http.*

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

