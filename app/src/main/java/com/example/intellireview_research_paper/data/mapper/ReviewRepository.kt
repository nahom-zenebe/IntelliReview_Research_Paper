package com.example.intellireview_research_paper.data.mapper

import com.example.intellireview_research_paper.model.reviewmodel


interface ReviewRepository {
    suspend fun getReview():List<reviewmodel>
    suspend fun createReview(paperId: String,userId: String,rating: Number,comment:String): reviewmodel
    suspend fun EditReview(notificationId:String,title: String,message: String): reviewmodel

}


