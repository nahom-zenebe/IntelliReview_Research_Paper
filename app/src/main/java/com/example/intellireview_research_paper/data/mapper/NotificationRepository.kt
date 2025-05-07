package com.example.intellireview_research_paper.data.mapper

import com.example.intellireview_research_paper.model.notificationmodel


interface  NotificationRepository {
    suspend fun getNotification():List<notificationmodel>
    suspend fun createNotification(title: String,message: String): notificationmodel
    suspend fun EditNotification(notificationId:String,title: String,message: String): notificationmodel

}


