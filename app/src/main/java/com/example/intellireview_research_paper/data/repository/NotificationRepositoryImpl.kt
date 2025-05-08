package com.example.intellireview_research_paper.data.repository

import com.example.intellireview_research_paper.data.mapper.NotificationRepository
import com.example.intellireview_research_paper.data.remote.NotificationApiClient
import com.example.intellireview_research_paper.model.notificationmodel

class NotificationRepositoryImpl:NotificationRepository {

    private val  api=NotificationApiClient.apiService



    override suspend fun getNotification():List<notificationmodel>{
        val response=api.getNotifications()
        return response.body() ?: emptyList()
    }
    override suspend fun createNotification(title: String,message: String): notificationmodel{
        val response=api.createNotification(title,message)
        return response.body()?:notificationmodel("","")
    }
    override suspend fun EditNotification(notificationId:String,title: String,message: String): notificationmodel{
        val response=api.editNotification(notificationId,title,message)
        return response.body()?:notificationmodel("","")
    }

}